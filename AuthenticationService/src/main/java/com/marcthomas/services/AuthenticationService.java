package com.marcthomas.services;

import com.marcthomas.model.JWTResponse;
import com.marcthomas.model.PublicCredentials;
import com.marcthomas.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marc.thomas on 20/05/2017.
 */
@RestController
public class AuthenticationService {
    @Autowired
    SecretService secretService;

    private Map<String, User> users = new HashMap<>();

    @PostConstruct
    public void initialiseSystem() {
        users.put("marc.thomas", new User("marc.thomas", "Marc Thomas", true));
        users.put("john.smith", new User("john.smith", "John Smith", false));
    }

    @RequestMapping("/get-public-key")
    public PublicCredentials getAuthenticationServicePublicKey() {
        return secretService.getMyPublicCreds();
    }

    @RequestMapping("/authenticate")
    public JWTResponse authenticateUser(String username) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            String jws = Jwts.builder()
                    .setHeaderParam("kid", secretService.getMyPublicCreds().getKeyId())
                    .setIssuer("AuthProvider")
                    .setSubject(user.getUsername())
                    .claim("name", user.getFullName())
                    .claim("accessToFilms", user.isAccessToFilms())
                    .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))   // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                    .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L))) // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                    .signWith(
                            SignatureAlgorithm.RS256,
                            secretService.getMyPrivateKey()
                    )
                    .compact();
            return new JWTResponse(jws);
        } else {
            // TODO handle HTTP error response
            return null;
        }
    }
}
