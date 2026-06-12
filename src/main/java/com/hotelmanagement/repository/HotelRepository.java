package com.hotelmanagement.repository;

import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.HotelStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    List<HotelEntity> findByOwner(
            UserEntity owner
    );

    List<HotelEntity> findByStatus(
            HotelStatus status
    );

    List<HotelEntity> findByStatusAndCityIgnoreCase(
            HotelStatus status,
            String city
    );

    List<HotelEntity> findByStatusAndAverageRatingGreaterThanEqual(
            HotelStatus status,
            Double rating
    );
}
