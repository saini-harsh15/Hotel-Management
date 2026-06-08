package com.hotelmanagement.repository;

import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.RoomEntity;
import com.hotelmanagement.entity.RoomTypeEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByCustomer(
            UserEntity customer
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

}
