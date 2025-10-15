package com.example.auditlogger.service;

import javax.annotation.PostConstruct;

public class AuditTrailService {

    private final AuditAuthenticator auditAuthenticator;

    private final AuditEventLogger auditEventLogger;

    private boolean isAuditLoggingEnabled = false;

    public AuditTrailService(AuditAuthenticator auditAuthenticator, AuditEventLogger auditEventLogger) {
        this.auditAuthenticator = auditAuthenticator;
        this.auditEventLogger = auditEventLogger;
    }

    @PostConstruct
    public void init() {
        if (auditAuthenticator.isAuthenticated()) {
        	isAuditLoggingEnabled = true;
            auditEventLogger.log("Audit Logger initialized", auditAuthenticator.getUsername());
        } else {
            throw new RuntimeException("Audit Logger could not authenticate!");
        }
    }

    public void logEvent(String event) {
        if (isAuditLoggingEnabled) {
        	auditEventLogger.log(event, auditAuthenticator.getUsername());
        } else {
            System.out.println("Unauthorized attempt to log event: " + event);
        }
    }
}
