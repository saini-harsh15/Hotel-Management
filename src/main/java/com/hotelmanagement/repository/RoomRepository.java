package com.hotelmanagement.repository;

import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.RoomEntity;
import com.hotelmanagement.entity.RoomTypeEntity;
import com.hotelmanagement.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository
        extends JpaRepository<RoomEntity, Long> {

    List<RoomEntity> findByRoomType(
            RoomTypeEntity roomType
    );


}