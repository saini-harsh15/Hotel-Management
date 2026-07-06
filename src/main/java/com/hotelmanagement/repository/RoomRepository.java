package com.hotelmanagement.repository;

import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.entity.RoomEntity;
import com.hotelmanagement.entity.RoomTypeEntity;
import com.hotelmanagement.enums.BookingStatus;
import com.hotelmanagement.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository
        extends JpaRepository<RoomEntity, Long> {

    List<RoomEntity> findByRoomType(
            RoomTypeEntity roomType
    );

    long countByHotel(
            HotelEntity hotel
    );

    long countByHotelAndStatus(
            HotelEntity hotel,
            RoomStatus status
    );

    long countByStatus(RoomStatus status);

}