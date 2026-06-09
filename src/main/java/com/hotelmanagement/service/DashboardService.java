package com.hotelmanagement.service;

import com.hotelmanagement.dto.response.DashboardResponseDTO;

public interface DashboardService {

    DashboardResponseDTO getHotelDashboard(
            Long hotelId
    );

}