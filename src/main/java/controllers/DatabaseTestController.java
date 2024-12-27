package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import service.DatabasePingService;

@RestController
public class DatabaseTestController {

    @Autowired
    private DatabasePingService databasePingService;

    @GetMapping("/ping-db")
    public String pingDatabase() {
        boolean isConnected = databasePingService.pingDatabase();
        return isConnected ? "Database is connected!" : "Database connection failed!";
    }
}
