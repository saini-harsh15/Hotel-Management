package com.hotelmanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomRequestDTO {

    @NotBlank
    private String roomNumber;

    @NotNull
    @Min(0)
    private Integer floorNumber;

}