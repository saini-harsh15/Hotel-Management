package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.LoginRequestDTO;
import com.hotelmanagement.dto.request.RegisterRequestDTO;
import com.hotelmanagement.dto.response.AuthResponseDTO;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.UserRole;
import com.hotelmanagement.enums.UserStatus;
import com.hotelmanagement.exception.InvalidCredentialsException;
import com.hotelmanagement.exception.ResourceAlreadyExistsException;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.security.JwtService;
import com.hotelmanagement.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new ResourceAlreadyExistsException("Email already exists");

        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {

            throw new ResourceAlreadyExistsException("Phone number already exists");

        }

        UserEntity user = UserEntity.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).phoneNumber(request.getPhoneNumber()).password(passwordEncoder.encode(request.getPassword())).role(UserRole.CUSTOMER).status(UserStatus.ACTIVE).build();

        UserEntity savedUser = userRepository.save(user);

        return AuthResponseDTO.builder().userId(savedUser.getId()).firstName(savedUser.getFirstName()).lastName(savedUser.getLastName()).email(savedUser.getEmail()).role(savedUser.getRole()).token(null).build();

    }

    @Override
    public AuthResponseDTO login(
            LoginRequestDTO request
    ) {

        UserEntity user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );

        boolean isPasswordValid =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!isPasswordValid) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );

        }

        String token = jwtService.generateToken(
                user.getEmail()
        );

        return AuthResponseDTO.builder()
                .token(token)
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }
}