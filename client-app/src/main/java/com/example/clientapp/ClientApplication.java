package com.example.clientapp;

import com.example.auditlogger.service.AuditLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Autowired
    private AuditLoggerService auditLoggerService;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        auditLoggerService.logEvent("User logged in");
        auditLoggerService.logEvent("Data updated");
    }
}
