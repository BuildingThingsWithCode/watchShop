package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.DatabaseService;

@RestController
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/ping-db")
    public String pingDatabase() {
        boolean isConnected = databaseService.pingDatabase();
        return isConnected ? "Database is connected!" : "Database connection failed!";
    }
    
    @PostMapping("/create-table")
    public String createTable(@RequestParam String tableName, @RequestParam String tableColumns) {
        boolean isTableCreated = databaseService.runSqlScriptToCreateTable(tableName, tableColumns);
        return isTableCreated ? "Table " + tableName + " created successfully!" : "Failed to create table " + tableName + ".";
    }
    
    @GetMapping("/columns")
    public List<String> getColumns(@RequestParam String tableName) {
        return databaseService.getTableColumns(tableName);
    }
 }
