package com.watchShop.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {


	private final JdbcTemplate jdbcTemplate;

	public ResponseEntity<String> pingDatabase() {
		try {
			jdbcTemplate.execute("SELECT * FROM images");
			return ResponseEntity.status(HttpStatus.OK).body("Database is connected!"); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Database connection failed! " + e.getMessage());  
		}
	}


	public ResponseEntity<String> createTable(String tableName, String tableColumns) {
		String createTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableColumns + ");";
		try {
			jdbcTemplate.execute(createTableSql); 
			return ResponseEntity.status(HttpStatus.OK).body("Table '" + tableName + "' created successfully (if not already present)."); 
		} catch (Exception e) {
			System.out.println("Error executing SQL script: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); 
		}
	}

	public List<String> getTableColumns(String tableName) {
		String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
		return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("column_name"), tableName);
	}	

	public List<String> getTableRows(String tableName) {
		String query = "SELECT name FROM " + tableName;  
		return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("name"));
	}

	}
