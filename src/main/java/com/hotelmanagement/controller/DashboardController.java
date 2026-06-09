package com.hotelmanagement.controller;

import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.DashboardResponseDTO;
import com.hotelmanagement.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<
            ApiResponseDTO<DashboardResponseDTO>
            > getHotelDashboard(
            @PathVariable Long hotelId
    ) {

        DashboardResponseDTO response =
                dashboardService.getHotelDashboard(
                        hotelId
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<DashboardResponseDTO>builder()
                        .success(true)
                        .message(
                                "Dashboard fetched successfully"
                        )
                        .data(response)
                        .build()
        );

    }

}