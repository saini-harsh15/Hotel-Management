package com.hotelmanagement.repository;

import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    List<HotelEntity> findByOwner(
            UserEntity owner
    );
}
