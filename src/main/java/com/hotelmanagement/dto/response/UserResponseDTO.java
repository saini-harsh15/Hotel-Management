package com.hotelmanagement.dto.response;

import com.hotelmanagement.enums.UserRole;
import com.hotelmanagement.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private String phoneNumber;

    private UserRole role;

    private UserStatus status;

    private Boolean emailVerified;

    private LocalDateTime createdAt;

}