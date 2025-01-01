package com.watchShop.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface DatabaseService {
	ResponseEntity<String> pingDatabase();
	ResponseEntity<String> createTable(String tableName, String tableColumns);
	List<String> getTableColumns(String tableName);
	List<String> getTableRows(String tableName);
}
