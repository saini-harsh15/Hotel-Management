package com.hotelmanagement.repository;

import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {
    List<RoomTypeEntity> findByHotel(
            HotelEntity hotel
    );
}
