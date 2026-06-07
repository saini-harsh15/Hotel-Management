package com.hotelmanagement.controller;

import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.HotelResponseDTO;
import com.hotelmanagement.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/hotels")
public class AdminHotelController {

    private final HotelService hotelService;

    public AdminHotelController(
            HotelService hotelService
    ) {
        this.hotelService = hotelService;
    }

    @PutMapping("/{hotelId}/approve")
    public ResponseEntity<
            ApiResponseDTO<HotelResponseDTO>
            > approveHotel(
            @PathVariable Long hotelId
    ) {

        HotelResponseDTO response =
                hotelService.approveHotel(
                        hotelId
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<HotelResponseDTO>builder()
                        .success(true)
                        .message(
                                "Hotel approved successfully"
                        )
                        .data(response)
                        .build()
        );

    }

}