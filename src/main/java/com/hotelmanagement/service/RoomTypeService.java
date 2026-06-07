package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.CreateRoomTypeRequestDTO;
import com.hotelmanagement.dto.response.RoomTypeResponseDTO;

import java.util.List;

public interface RoomTypeService {

    RoomTypeResponseDTO createRoomType(
            Long hotelId,
            CreateRoomTypeRequestDTO request
    );

    List<RoomTypeResponseDTO> getRoomTypesByHotel(
            Long hotelId
    );

}