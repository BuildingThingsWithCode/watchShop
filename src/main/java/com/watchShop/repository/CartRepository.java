package com.watchShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.watchShop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
