package com.hotelmanagement.repository;

import com.hotelmanagement.entity.AmenityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<AmenityEntity, Long> {
}
