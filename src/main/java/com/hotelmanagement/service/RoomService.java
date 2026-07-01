package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.CreateRoomRequestDTO;
import com.hotelmanagement.dto.request.UpdateRoomRequestDTO;
import com.hotelmanagement.dto.response.RoomResponseDTO;

import java.util.List;

public interface RoomService {

    RoomResponseDTO createRoom(
            Long roomTypeId,
            CreateRoomRequestDTO request
    );

    RoomResponseDTO updateRoom(
            Long roomId,
            UpdateRoomRequestDTO request
    );

    List<RoomResponseDTO> getRoomsByRoomType(
            Long roomTypeId
    );

    RoomResponseDTO getRoomById(
            Long roomId
    );
}