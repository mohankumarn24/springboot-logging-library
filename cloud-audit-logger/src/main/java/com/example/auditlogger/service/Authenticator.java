package com.example.auditlogger.service;

public class Authenticator {

    private final String username;

    private final String password;

    private final String token;

    public Authenticator(String username, String password, String token) {

        if(username.isBlank() || password.isBlank() || token.isBlank()) {
            throw new IllegalArgumentException("Credentials must be provided");
        }

        this.username = username;
        this.password = password;
        this.token = token;
    }

    public boolean authenticate() {
        return true; // replace with real cloud authentication
    }

    public String getUsername() {
        return username;
    }
}
