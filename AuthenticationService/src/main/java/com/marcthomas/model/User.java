package com.marcthomas.model;

/**
 * Created by marc.thomas on 20/05/2017.
 */
public class User {
    private String username;
    private String fullName;
    private boolean accessToFilms;

    public User(String username, String fullName, boolean accessToFilms) {
        this.username = username;
        this.fullName = fullName;
        this.accessToFilms = accessToFilms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isAccessToFilms() {
        return accessToFilms;
    }

    public void setAccessToFilms(boolean accessToFilms) {
        this.accessToFilms = accessToFilms;
    }
}
