package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabasePingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean pingDatabase() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return true;  
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
}
