package com.example.auditlogger.service;

import javax.annotation.PostConstruct;

public class AuditLoggerService {

    private final Authenticator authenticator;

    private final AuditEventLogger eventLogger;

    private boolean loggingEnabled = false;

    public AuditLoggerService(Authenticator authenticator, AuditEventLogger eventLogger) {
        this.authenticator = authenticator;
        this.eventLogger = eventLogger;
    }

    @PostConstruct
    public void init() {
        if (authenticator.authenticate()) {
            loggingEnabled = true;
            eventLogger.log("Audit Logger Initialized", authenticator.getUsername());
        } else {
            throw new RuntimeException("Audit Logger could not authenticate!");
        }
    }

    public void logEvent(String event) {
        if (loggingEnabled) {
            eventLogger.log(event, authenticator.getUsername());
        } else {
            System.out.println("Unauthorized attempt to log event: " + event);
        }
    }
}
