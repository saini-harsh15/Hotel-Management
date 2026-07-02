package com.hotelmanagement.controller;

import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.HotelResponseDTO;
import com.hotelmanagement.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{hotelId}/reject")
    public ResponseEntity<
            ApiResponseDTO<HotelResponseDTO>
            > rejectHotel(
            @PathVariable Long hotelId
    ) {

        HotelResponseDTO response =
                hotelService.rejectHotel(
                        hotelId
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<HotelResponseDTO>builder()
                        .success(true)
                        .message(
                                "Hotel rejected successfully"
                        )
                        .data(response)
                        .build()
        );

    }


    @GetMapping("/pending")
    public ResponseEntity<
            ApiResponseDTO<List<HotelResponseDTO>>
            > getPendingHotels() {

        List<HotelResponseDTO> response =
                hotelService.getPendingHotels();

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<HotelResponseDTO>>builder()
                        .success(true)
                        .message(
                                "Pending hotels fetched successfully"
                        )
                        .data(response)
                        .build()
        );

    }

}