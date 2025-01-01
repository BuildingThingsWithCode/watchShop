package com.watchShop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watchShop.model.Watch;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {
    Optional<Watch> findByImageId(Long imageId);
}
