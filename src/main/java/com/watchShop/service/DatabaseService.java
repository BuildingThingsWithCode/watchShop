package com.watchShop.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface DatabaseService {
	ResponseEntity<String>  pingDatabase();
	ResponseEntity<String>  createTable(String tableName, String tableColumns);//done
	List<String> getTableColumns(String tableName);
	List<String> getTableRows(String tableName);
}
