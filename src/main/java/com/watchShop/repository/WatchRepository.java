package com.watchShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watchShop.model.Watch;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {
}
