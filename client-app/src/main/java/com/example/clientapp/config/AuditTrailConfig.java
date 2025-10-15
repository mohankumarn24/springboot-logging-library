package com.example.clientapp.config;

import com.example.auditlogger.service.AuditEventLogger;
import com.example.auditlogger.service.AuditTrailService;
import com.example.auditlogger.service.AuditAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * In a @Configuration class
 *  - Spring uses CGLIB proxies to subclass your configuration class
 *  - Because of this, @Bean methods can be public, protected, or package-private (default)
 *  - Private won’t work in a normal @Configuration class, because the subclass proxy cannot override private methods
 */
@Configuration
public class AuditTrailConfig {

    @Bean
    protected AuditAuthenticator authenticator() {	// allowed
        return new AuditAuthenticator("clientUser", "clientPass", "clientToken");
    }

    @Bean
    AuditEventLogger auditEventLogger() {		// package-private, allowed
        return new AuditEventLogger();
    }

    @Bean
    AuditTrailService auditLoggerService(AuditAuthenticator auditAuthenticator, AuditEventLogger auditEventLogger) {
        return new AuditTrailService(auditAuthenticator, auditEventLogger);
    }
    
    // @Bean
    // private AnotherService anotherService() {  // won't work
    //     return new AnotherService();
    // }    
}
