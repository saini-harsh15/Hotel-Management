package com.hotelmanagement.dto.request;

import com.hotelmanagement.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserStatusRequestDTO {

    @NotNull
    private UserStatus status;

}