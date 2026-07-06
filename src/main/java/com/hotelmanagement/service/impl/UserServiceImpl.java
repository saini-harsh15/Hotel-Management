package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.UpdateUserRoleRequestDTO;
import com.hotelmanagement.dto.request.UpdateUserStatusRequestDTO;
import com.hotelmanagement.dto.response.UserResponseDTO;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.UserRole;
import com.hotelmanagement.enums.UserStatus;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.exception.UnauthorizedOperationException;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    private void validateSuperAdmin() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        UserEntity admin =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        if (admin.getRole() != UserRole.SUPER_ADMIN) {

            throw new UnauthorizedOperationException(
                    "Only SUPER_ADMIN can perform this action"
            );

        }

    }

    private UserResponseDTO mapToResponse(
            UserEntity user
    ) {

        return UserResponseDTO.builder()

                .id(user.getId())

                .firstName(user.getFirstName())

                .lastName(user.getLastName())

                .fullName(
                        user.getFirstName()
                                + " "
                                + user.getLastName()
                )

                .email(user.getEmail())

                .phoneNumber(
                        user.getPhoneNumber()
                )

                .role(user.getRole())

                .status(user.getStatus())

                .emailVerified(
                        user.getEmailVerified()
                )

                .createdAt(
                        user.getCreatedAt()
                )

                .build();

    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        validateSuperAdmin();

        return userRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public UserResponseDTO getUserById(
            Long userId
    ) {

        validateSuperAdmin();

        UserEntity user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return mapToResponse(user);

    }

    @Override
    public List<UserResponseDTO> getUsersByRole(
            UserRole role
    ) {

        validateSuperAdmin();

        return userRepository
                .findByRole(role)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public UserResponseDTO updateUserRole(
            Long userId,
            UpdateUserRoleRequestDTO request
    ) {

        validateSuperAdmin();

        UserEntity user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        if (user.getRole() == UserRole.SUPER_ADMIN) {

            throw new IllegalArgumentException(
                    "Cannot modify a SUPER_ADMIN."
            );

        }

        user.setRole(
                request.getRole()
        );

        UserEntity updated =
                userRepository.save(
                        user
                );

        return mapToResponse(
                updated
        );

    }

    @Override
    public UserResponseDTO updateUserStatus(
            Long userId,
            UpdateUserStatusRequestDTO request
    ) {

        validateSuperAdmin();

        UserEntity user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        if (user.getRole() == UserRole.SUPER_ADMIN) {

            throw new IllegalArgumentException(
                    "Cannot modify a SUPER_ADMIN."
            );

        }

        if (request.getStatus()
                == UserStatus.PENDING_VERIFICATION) {

            throw new IllegalArgumentException(
                    "Cannot manually assign PENDING_VERIFICATION."
            );

        }

        user.setStatus(
                request.getStatus()
        );

        UserEntity updated =
                userRepository.save(
                        user
                );

        return mapToResponse(
                updated
        );

    }

}