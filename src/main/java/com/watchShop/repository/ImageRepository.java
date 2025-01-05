package com.watchShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watchShop.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	
}
