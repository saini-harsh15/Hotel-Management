package com.hotelmanagement.repository;

import com.hotelmanagement.entity.*;
import com.hotelmanagement.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByCustomer(
            UserEntity customer
    );

    List<BookingEntity> findByRoomTypeHotel(
            HotelEntity hotel
    );

    @Query("""
SELECT COUNT(b)
FROM BookingEntity b
WHERE b.roomType = :roomType
AND b.status IN :statuses
AND b.checkInDate < :checkOutDate
AND b.checkOutDate > :checkInDate
""")
    long countOverlappingBookings(
            @Param("roomType") RoomTypeEntity roomType,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("statuses") List<BookingStatus> statuses
    );

    List<BookingEntity> findByAssignedRoomAndStatusIn(
            RoomEntity room,
            List<BookingStatus> statuses
    );

    long countByRoomTypeHotel(
            HotelEntity hotel
    );

    long countByRoomTypeHotelAndStatus(
            HotelEntity hotel,
            BookingStatus status
    );


    @Query("""
            SELECT COALESCE(SUM(b.totalAmount),0)
            FROM BookingEntity b
            WHERE b.roomType.hotel = :hotel
            AND b.status = :status
            """)
    BigDecimal getTotalRevenue(
            @Param("hotel") HotelEntity hotel,
            @Param("status") BookingStatus status
    );

    @Query("""
SELECT COUNT(b) > 0
FROM BookingEntity b
WHERE b.assignedRoom = :room
AND b.status IN :statuses
""")
    boolean hasActiveBookings(
            @Param("room") RoomEntity room,
            @Param("statuses") List<BookingStatus> statuses
    );

    long countByStatus(BookingStatus status);

}
