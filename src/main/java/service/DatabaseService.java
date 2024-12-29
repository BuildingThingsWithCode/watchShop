package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean pingDatabase() {
        try {
            jdbcTemplate.execute("SELECT * FROM images");
            return true;  
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
    
    
    public boolean runSqlScriptToCreateTable(String tableName, String tableColumns) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableColumns + ");";
        try {
            jdbcTemplate.execute(createTableSql); 
            System.out.println("Table '" + tableName + "' created successfully (if not already present).");
            return true;
        } catch (Exception e) {
            System.out.println("Error executing SQL script: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> getTableColumns(String tableName) {
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("column_name"), tableName);
    }	
    
}
