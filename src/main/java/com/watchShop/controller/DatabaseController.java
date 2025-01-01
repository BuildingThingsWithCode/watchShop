package com.watchShop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watchShop.model.Watch;
import com.watchShop.service.DatabaseService;
import com.watchShop.service.WatchService;

@RestController
public class DatabaseController {

	@Autowired
	private DatabaseService databaseService;
	@Autowired
	private WatchService watchService;
	
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
	
	@GetMapping("/watch/description/{id}")
	public ResponseEntity<String> getWatchDescription(@PathVariable Long id) {
	    try {
	         Optional<Watch> watch = watchService.getWatchById(id);
	         if (watch.isPresent()) {
	            return ResponseEntity.ok(watch.get().getDescription()); 
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Watch with ID " + id + " not found.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch watch description: " + e.getMessage());
	    }
	}

	@GetMapping("/rows")
	public List<String> getRows(@RequestParam String tableName) {
		return databaseService.getTableRows(tableName);
	}
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWatch(@PathVariable Long id) {
        try {
            databaseService.deleteWatchById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Watch with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete watch: " + e.getMessage());
        }
    }
}
