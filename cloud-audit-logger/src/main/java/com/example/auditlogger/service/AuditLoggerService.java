package com.example.auditlogger.service;

public class AuditLoggerService {

    private final Authenticator authenticator;

    private final AuditEventLogger eventLogger;

    public AuditLoggerService(Authenticator authenticator, AuditEventLogger eventLogger) {
        this.authenticator = authenticator;
        this.eventLogger = eventLogger;
    }

    public void logEvent(String event) {
        if(authenticator.authenticate()) {
            eventLogger.log(event, authenticator.getUsername());
        } else {
            System.out.println("Unauthorized attempt to log event: " + event);
        }
    }
}
