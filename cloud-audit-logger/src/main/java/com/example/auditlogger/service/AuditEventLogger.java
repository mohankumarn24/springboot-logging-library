package com.example.auditlogger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditEventLogger {

    private static final Logger logger = LoggerFactory.getLogger(AuditEventLogger.class);

    public void log(String event, String username) {
        logger.info("AUDIT EVENT: {} | User: {}", event, username);
    }
}
