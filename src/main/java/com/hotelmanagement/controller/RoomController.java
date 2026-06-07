package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.CreateRoomRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.RoomResponseDTO;
import com.hotelmanagement.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types/{roomTypeId}/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponseDTO<RoomResponseDTO>> getRoomById(@PathVariable Long roomId) {

        RoomResponseDTO response = roomService.getRoomById(roomId);

        return ResponseEntity.ok(ApiResponseDTO.<RoomResponseDTO>builder().success(true).message("Room fetched successfully").data(response).build());

    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RoomResponseDTO>>> getRoomsByRoomType(@PathVariable Long roomTypeId) {

        List<RoomResponseDTO> response = roomService.getRoomsByRoomType(roomTypeId);

        return ResponseEntity.ok(ApiResponseDTO.<List<RoomResponseDTO>>builder().success(true).message("Rooms fetched successfully").data(response).build());

    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<RoomResponseDTO>> createRoom(@PathVariable Long roomTypeId, @Valid @RequestBody CreateRoomRequestDTO request) {

        RoomResponseDTO response = roomService.createRoom(roomTypeId, request);

        return ResponseEntity.ok(ApiResponseDTO.<RoomResponseDTO>builder().success(true).message("Room created successfully").data(response).build());

    }

}