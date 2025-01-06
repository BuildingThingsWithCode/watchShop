package com.watchShop.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.watchShop.exception.DatabaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {


	private final JdbcTemplate jdbcTemplate;

	public ResponseEntity<String> pingDatabase() {
		try {
			jdbcTemplate.execute("SELECT VERSION();");
			return ResponseEntity.status(HttpStatus.OK).body("Database is connected!"); 
		} catch (Exception e) {
			throw new DatabaseException("Database connection failed! ", e.getCause());
		}
	}


	public ResponseEntity<String> createTable(String tableName, String tableColumns) {
		String createTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableColumns + ");";
		try {
			jdbcTemplate.execute(createTableSql); 
			return ResponseEntity.status(HttpStatus.OK).body("Table '" + tableName + "' created successfully (if not already present)."); 
		} catch (Exception e) {
			throw new DatabaseException("Database table " + tableName + " could not be created", e.getCause()); 
		}
	}

	public List<String> getTableColumns(String tableName) {
		String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
		try {
			return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("column_name"), tableName);
		} catch (Exception e) {
			throw new DatabaseException("Could not fetch columns from database table " + tableName + ".", e.getCause()); 
		}
	}	

	public List<String> getTableRows(String tableName) {
		String query = "SELECT * FROM TableName" + tableName;  
		try {
			return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("name"));
		} catch (Exception e) {
			System.out.println(e.toString());
			throw new DatabaseException("Could not fetch rows from database table " + tableName + ".", e.getCause()); 
		}
	}

}
