package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.CreateRoomTypeRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.RoomTypeResponseDTO;
import com.hotelmanagement.service.RoomTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels/{hotelId}/room-types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RoomTypeResponseDTO>>> getRoomTypesByHotel(@PathVariable Long hotelId) {

        List<RoomTypeResponseDTO> response = roomTypeService.getRoomTypesByHotel(hotelId);

        return ResponseEntity.ok(ApiResponseDTO.<List<RoomTypeResponseDTO>>builder().success(true).message("Room types fetched successfully").data(response).build());

    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<RoomTypeResponseDTO>> createRoomType(@PathVariable Long hotelId, @Valid @RequestBody CreateRoomTypeRequestDTO request) {

        RoomTypeResponseDTO response = roomTypeService.createRoomType(hotelId, request);

        return ResponseEntity.ok(ApiResponseDTO.<RoomTypeResponseDTO>builder().success(true).message("Room type created successfully").data(response).build());

    }

}