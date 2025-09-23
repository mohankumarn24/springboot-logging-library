package com.example.clientapp.config;

import com.example.auditlogger.service.AuditEventLogger;
import com.example.auditlogger.service.AuditLoggerService;
import com.example.auditlogger.service.Authenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientAuditConfig {

    @Bean
    public Authenticator authenticator() {
        return new Authenticator("clientUser", "clientPass", "clientToken");
    }

    @Bean
    public AuditEventLogger auditEventLogger() {
        return new AuditEventLogger();
    }

    @Bean
    public AuditLoggerService auditLoggerService(Authenticator authenticator, AuditEventLogger eventLogger) {
        return new AuditLoggerService(authenticator, eventLogger);
    }
}
