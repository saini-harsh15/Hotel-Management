package com.hotelmanagement.repository;

import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<ReviewEntity, Long> {

    boolean existsByBooking(
            BookingEntity booking
    );

    List<ReviewEntity> findByBookingRoomTypeHotelId(
            Long hotelId
    );

    List<ReviewEntity> findByBookingRoomTypeHotel(
            HotelEntity hotel
    );


    @Query("""
SELECT COALESCE(AVG(r.rating),0)
FROM ReviewEntity r
""")
    Double getAveragePlatformRating();

}