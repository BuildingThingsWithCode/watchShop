package com.watchShop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@Column(name = "created_date")
	private Date createdDate;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();
	private BigDecimal sum = BigDecimal.ZERO;

	public Cart() {
	}

	public Cart(Integer userId) {
		this.userId = userId;
		this.createdDate = new Date();
	}

	public void addItem(CartItem cartItem) {
		for (CartItem item : items) {
			if (item.equals(cartItem)) {
				item.setQuantity(item.getQuantity() + cartItem.getQuantity());
			}
			else items.add(cartItem);
		}
		addAmount(cartItem);
	}

	public void addItem(Watch watch) {
		for (CartItem item : items) {
			if (item.getWatch().getBrand().equals(watch.getBrand()) && item.getWatch().getName().equals(watch.getName())) {
				item.setQuantity(item.getQuantity() + 1);
				addAmount(watch);
			}
			else items.add(new CartItem(this, watch, 1));
		}
		addAmount(watch);
	}

	private void addAmount(CartItem cartItem) {
		BigDecimal toAdd = cartItem.getWatch().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
		sum = sum.add(toAdd);
	}
	
	private void addAmount(Watch watch) {
		sum = sum.add(watch.getPrice());
	}
}
