package com.marcthomas.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Code taken from another GitHub repo https://github.com/stormpath/JavaRoadStorm2016/tree/master/roadstorm-jwt-microservices-tutorial
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JWTResponse {
    private String exceptionType;
    private String jwt;
    private Jws<Claims> jwsClaims;
    private String message;
    private Status status;

    public enum Status {
        SUCCESS, ERROR
    }

    public JWTResponse() {}

    public JWTResponse(String jwt) {
        this.jwt = jwt;
        setStatus(Status.SUCCESS);
    }

    public JWTResponse(Jws<Claims> jwsClaims) {
        this.jwsClaims = jwsClaims;
        setStatus(Status.SUCCESS);
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Jws<Claims> getJwsClaims() {
        return jwsClaims;
    }

    public void setJwsClaims(Jws<Claims> jwsClaims) {
        this.jwsClaims = jwsClaims;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

