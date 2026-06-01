package com.hotelmanagement.dto.response;

import com.hotelmanagement.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String token;

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private UserRole role;

}