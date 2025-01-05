package com.watchShop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watchShop.service.DatabaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/db")
@RequiredArgsConstructor
public class DatabaseController {

	private final DatabaseService databaseService;

	@GetMapping("/ping")
	public ResponseEntity<String>  pingDatabase() {
		return databaseService.pingDatabase();
	}

	@PostMapping("/create/{tableName}")
	public ResponseEntity<String>  createTable(@PathVariable String tableName, @RequestParam String tableColumns) {
		return databaseService.createTable(tableName, tableColumns);
	}

	@GetMapping("/{tableName}/columns")
	public List<String> getColumns(@PathVariable String tableName) {
		return databaseService.getTableColumns(tableName);
	}

	@GetMapping("/{tableName}/rows")
	public List<String> getRows(@PathVariable String tableName) {
		return databaseService.getTableRows(tableName);
	}
}
