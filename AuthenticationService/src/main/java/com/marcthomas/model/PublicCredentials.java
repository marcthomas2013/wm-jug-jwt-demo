package com.marcthomas.model;

/**
 * Code taken from another GitHub repo https://github.com/stormpath/JavaRoadStorm2016/tree/master/roadstorm-jwt-microservices-tutorial
 */
public class PublicCredentials {
    final String keyId;
    final String b64UrlPublicKey;

    public PublicCredentials(String kid, String b64UrlPublicKey) {
        this.keyId = kid;
        this.b64UrlPublicKey = b64UrlPublicKey;
    }

    public String getKeyId() {
        return keyId;
    }

    public String getB64UrlPublicKey() {
        return b64UrlPublicKey;
    }
}