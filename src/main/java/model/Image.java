package model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String path;

    @Column(name = "uploaded_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime uploadedAt = LocalDateTime.now();
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setPath(String path) {
    	this.path = path;
    }
}
