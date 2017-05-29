package com.marcthomas.services;
import com.marcthomas.model.PublicCredentials;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import io.jsonwebtoken.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Code taken from another GitHub repo https://github.com/stormpath/JavaRoadStorm2016/tree/master/roadstorm-jwt-microservices-tutorial
 */
@Service
public class SecretService {

    private static final Logger log = LoggerFactory.getLogger(SecretService.class);

    private KeyPair myKeyPair;
    private String keyId;

    private Map<String, PublicKey> publicKeys = new HashMap<>();

    @PostConstruct
    public void setup() {
        refreshMyCreds();
    }

    private SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            String kid = header.getKeyId();
            if (!Strings.hasText(kid)) {
                throw new JwtException("Missing required 'keyId' header param in JWT with claims: " + claims);
            }
            Key key = publicKeys.get(kid);
            if (key == null) {
                throw new JwtException("No public key registered for keyId: " + kid + ". JWT claims: " + claims);
            }
            return key;
        }
    };

    public SigningKeyResolver getSigningKeyResolver() {
        return signingKeyResolver;
    }

    public PublicCredentials getPublicCreds(String kid) {
        return createPublicCreds(kid, publicKeys.get(kid));
    }

    public PublicCredentials getMyPublicCreds() {
        return createPublicCreds(this.keyId, myKeyPair.getPublic());
    }

    private PublicCredentials createPublicCreds(String kid, PublicKey key) {
        return new PublicCredentials(kid, TextCodec.BASE64URL.encode(key.getEncoded()));
    }

    // do not expose in controllers
    public PrivateKey getMyPrivateKey() {
        return myKeyPair.getPrivate();
    }

    public PublicCredentials refreshMyCreds() {
        myKeyPair = RsaProvider.generateKeyPair(1024);
        keyId = UUID.randomUUID().toString();

        PublicCredentials publicCredentials = getMyPublicCreds();

        // this microservice will trust itself
        addPublicCreds(publicCredentials);

        return publicCredentials;
    }

    public void addPublicCreds(PublicCredentials publicCredentials) {
        byte[] encoded = TextCodec.BASE64URL.decode(publicCredentials.getB64UrlPublicKey());

        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encoded));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Unable to create public key: {}", e.getMessage(), e);
        }

        publicKeys.put(publicCredentials.getKeyId(), publicKey);
    }
}
