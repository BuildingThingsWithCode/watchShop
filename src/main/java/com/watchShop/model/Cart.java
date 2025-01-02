package com.watchShop.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name="cart")
@Data
public class Cart {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_id")
    private Integer userId;

    @Column(name = "watch_id")
    private Long watchId;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne
    @JoinColumn(name = "watch_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Watch watch;

    public Cart(Integer userId, Long watchId) {
    	this.userId = userId;
    	this.watchId = watchId;
    	this.createdDate = Instant.now();
    }

}
