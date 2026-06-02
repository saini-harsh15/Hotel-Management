package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.CreateHotelRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.HotelResponseDTO;
import com.hotelmanagement.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(
            HotelService hotelService
    ) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<
            ApiResponseDTO<HotelResponseDTO>
            > createHotel(
            @Valid
            @RequestBody
            CreateHotelRequestDTO request
    ) {

        HotelResponseDTO response =
                hotelService.createHotel(
                        request
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<HotelResponseDTO>builder()
                        .success(true)
                        .message(
                                "Hotel created successfully"
                        )
                        .data(response)
                        .build()
        );

    }
    @GetMapping("/my-hotels")
    public ResponseEntity<
            ApiResponseDTO<List<HotelResponseDTO>>
            > getMyHotels() {

        List<HotelResponseDTO> response =
                hotelService.getMyHotels();

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<HotelResponseDTO>>builder()
                        .success(true)
                        .message("Hotels fetched successfully")
                        .data(response)
                        .build()
        );

    }

}