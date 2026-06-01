package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.LoginRequestDTO;
import com.hotelmanagement.dto.request.RegisterRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.AuthResponseDTO;
import com.hotelmanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {

        AuthResponseDTO response =
                authService.register(request);

        return ResponseEntity.ok(
                ApiResponseDTO.<AuthResponseDTO>builder()
                        .success(true)
                        .message("User registered successfully")
                        .data(response)
                        .build()
        );

    }


    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>>
    login(
            @Valid @RequestBody
            LoginRequestDTO request
    ) {

        AuthResponseDTO response =
                authService.login(request);

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<AuthResponseDTO>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/hello")
    public String hello() {

        return "Hello JWT";

    }

    @GetMapping("/me")
    public String me() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return authentication.getName();

    }

}