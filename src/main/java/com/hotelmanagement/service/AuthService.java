package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.LoginRequestDTO;
import com.hotelmanagement.dto.request.RegisterRequestDTO;
import com.hotelmanagement.dto.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

}