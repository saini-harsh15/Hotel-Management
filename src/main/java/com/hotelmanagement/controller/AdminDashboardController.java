package com.hotelmanagement.controller;

import com.hotelmanagement.dto.response.AdminDashboardResponseDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.service.AdminDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(
            AdminDashboardService adminDashboardService
    ) {
        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping
    public ResponseEntity<
            ApiResponseDTO<AdminDashboardResponseDTO>
            > getDashboard() {

        AdminDashboardResponseDTO response =
                adminDashboardService.getDashboard();

        return ResponseEntity.ok(

                ApiResponseDTO
                        .<AdminDashboardResponseDTO>builder()
                        .success(true)
                        .message("Dashboard analytics fetched successfully.")
                        .data(response)
                        .build()

        );

    }

}