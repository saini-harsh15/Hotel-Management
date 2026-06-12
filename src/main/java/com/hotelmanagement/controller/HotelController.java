package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.CreateHotelRequestDTO;
import com.hotelmanagement.dto.request.UpdateHotelRequestDTO;
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

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<HotelResponseDTO>> createHotel(@Valid @RequestBody CreateHotelRequestDTO request) {

        HotelResponseDTO response = hotelService.createHotel(request);

        return ResponseEntity.ok(ApiResponseDTO.<HotelResponseDTO>builder().success(true).message("Hotel created successfully").data(response).build());

    }

    @GetMapping("/search/city")
    public ResponseEntity<ApiResponseDTO<List<HotelResponseDTO>>> searchHotelsByCity(@RequestParam String city) {

        List<HotelResponseDTO> response = hotelService.searchHotelsByCity(city);

        return ResponseEntity.ok(ApiResponseDTO.<List<HotelResponseDTO>>builder().success(true).message("Hotels fetched successfully").data(response).build());

    }

    @GetMapping("/search/rating")
    public ResponseEntity<ApiResponseDTO<List<HotelResponseDTO>>> searchHotelsByRating(@RequestParam Double minRating) {

        List<HotelResponseDTO> response = hotelService.searchHotelsByRating(minRating);

        return ResponseEntity.ok(ApiResponseDTO .<List<HotelResponseDTO>>builder().success(true).message("Hotels fetched successfully").data(response).build());

    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<ApiResponseDTO<HotelResponseDTO>> getHotelById(@PathVariable Long hotelId) {

        HotelResponseDTO response = hotelService.getHotelById(hotelId);

        return ResponseEntity.ok(ApiResponseDTO.<HotelResponseDTO>builder().success(true).message("Hotel fetched successfully").data(response).build());

    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<HotelResponseDTO>>> getAllHotels() {

        List<HotelResponseDTO> response = hotelService.getAllApprovedHotels();

        return ResponseEntity.ok(ApiResponseDTO.<List<HotelResponseDTO>>builder().success(true).message("Hotels fetched successfully").data(response).build());

    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<ApiResponseDTO<HotelResponseDTO>> updateHotel(@PathVariable Long hotelId, @Valid @RequestBody UpdateHotelRequestDTO request) {

        HotelResponseDTO response = hotelService.updateHotel(hotelId, request);

        return ResponseEntity.ok(ApiResponseDTO.<HotelResponseDTO>builder().success(true).message("Hotel updated successfully").data(response).build());

    }

    @GetMapping("/my-hotels")
    public ResponseEntity<ApiResponseDTO<List<HotelResponseDTO>>> getMyHotels() {

        List<HotelResponseDTO> response = hotelService.getMyHotels();

        return ResponseEntity.ok(ApiResponseDTO.<List<HotelResponseDTO>>builder().success(true).message("Hotels fetched successfully").data(response).build());

    }

}