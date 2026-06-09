package com.hotelmanagement.repository;

import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<ReviewEntity, Long> {

    boolean existsByBooking(
            BookingEntity booking
    );

    List<ReviewEntity> findByBookingRoomTypeHotelId(
            Long hotelId
    );

}