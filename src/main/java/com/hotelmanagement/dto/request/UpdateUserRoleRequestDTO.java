package com.hotelmanagement.dto.request;

import com.hotelmanagement.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequestDTO {

    @NotNull
    private UserRole role;

}