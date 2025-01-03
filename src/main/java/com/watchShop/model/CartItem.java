package com.watchShop.model;

import java.util.Date;

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
@Table(name = "cart_item")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "watch_id", nullable = false)
    private Watch watch;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "added_date", nullable = false)
    private Date addedDate;

    public CartItem() {
    }

    public CartItem(Cart cart, Watch watch, int quantity) {
        this.watch = watch;
        this.cart = cart;
        this.quantity = quantity;
        this.addedDate = new Date();
    }
}
