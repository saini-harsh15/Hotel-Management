package com.hotelmanagement.repository;

import com.hotelmanagement.entity.HotelImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelImageRepository extends JpaRepository<HotelImageEntity, Long> {
}
