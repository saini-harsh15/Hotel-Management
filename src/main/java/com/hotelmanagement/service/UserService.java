package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.UpdateUserRoleRequestDTO;
import com.hotelmanagement.dto.request.UpdateUserStatusRequestDTO;
import com.hotelmanagement.dto.response.UserResponseDTO;
import com.hotelmanagement.enums.UserRole;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(
            Long userId
    );

    List<UserResponseDTO> getUsersByRole(
            UserRole role
    );

    UserResponseDTO updateUserRole(
            Long userId,
            UpdateUserRoleRequestDTO request
    );

    UserResponseDTO updateUserStatus(
            Long userId,
            UpdateUserStatusRequestDTO request
    );

}