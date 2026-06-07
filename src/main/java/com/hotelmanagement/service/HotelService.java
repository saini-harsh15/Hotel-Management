package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.CreateHotelRequestDTO;
import com.hotelmanagement.dto.request.UpdateHotelRequestDTO;
import com.hotelmanagement.dto.response.HotelResponseDTO;

import java.util.List;

public interface HotelService {



    List<HotelResponseDTO> getAllApprovedHotels();

    HotelResponseDTO approveHotel(
            Long hotelId
    );

    HotelResponseDTO updateHotel(
            Long hotelId,
            UpdateHotelRequestDTO request
    );

    HotelResponseDTO getHotelById(
            Long hotelId
    );

    HotelResponseDTO createHotel(
            CreateHotelRequestDTO request
    );

    List<HotelResponseDTO> getMyHotels();
}