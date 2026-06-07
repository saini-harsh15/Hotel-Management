package com.hotelmanagement.repository;

import com.hotelmanagement.entity.RoomTypeEntity;
import com.hotelmanagement.entity.RoomTypeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hotelmanagement.entity.HotelEntity;
import java.util.List;

public interface RoomTypeImageRepository extends JpaRepository<RoomTypeImageEntity, Long> {


}
