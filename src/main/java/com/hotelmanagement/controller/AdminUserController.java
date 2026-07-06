package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.UpdateUserRoleRequestDTO;
import com.hotelmanagement.dto.request.UpdateUserStatusRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.UserResponseDTO;
import com.hotelmanagement.enums.UserRole;
import com.hotelmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>>
    getAllUsers() {

        List<UserResponseDTO> response =
                userService.getAllUsers();

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<UserResponseDTO>>builder()
                        .success(true)
                        .message("Users fetched successfully")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>>
    getUserById(
            @PathVariable Long userId
    ) {

        UserResponseDTO response =
                userService.getUserById(
                        userId
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<UserResponseDTO>builder()
                        .success(true)
                        .message("User fetched successfully")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/role")
    public ResponseEntity<ApiResponseDTO<List<UserResponseDTO>>>
    getUsersByRole(
            @RequestParam UserRole role
    ) {

        List<UserResponseDTO> response =
                userService.getUsersByRole(
                        role
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<UserResponseDTO>>builder()
                        .success(true)
                        .message("Users fetched successfully")
                        .data(response)
                        .build()
        );

    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>>
    updateUserRole(
            @PathVariable Long userId,
            @Valid
            @RequestBody
            UpdateUserRoleRequestDTO request
    ) {

        UserResponseDTO response =
                userService.updateUserRole(
                        userId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<UserResponseDTO>builder()
                        .success(true)
                        .message("User role updated successfully")
                        .data(response)
                        .build()
        );

    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>>
    updateUserStatus(
            @PathVariable Long userId,
            @Valid
            @RequestBody
            UpdateUserStatusRequestDTO request
    ) {

        UserResponseDTO response =
                userService.updateUserStatus(
                        userId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<UserResponseDTO>builder()
                        .success(true)
                        .message("User status updated successfully")
                        .data(response)
                        .build()
        );

    }

}