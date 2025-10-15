package com.example.auditlogger.service;

public class AuditAuthenticator {

    private final String username;

    private final String password;

    private final String token;

    public AuditAuthenticator(String username, String password, String token) {

        if(isNullOrBlank(username) || isNullOrBlank(password) || isNullOrBlank(token)) {
            throw new IllegalArgumentException("Credentials must be provided");
        }

        this.username = username;
        this.password = password;
        this.token = token;
    }

    public boolean isAuthenticated() {
        return true; // replace with real cloud authentication
    }

    public String getUsername() {
        return username;
    }
    
    private boolean isNullOrBlank(String param) {
    	return param == null || param.isBlank();
    }
}
