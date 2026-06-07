package com.hotelmanagement.dto.request;

import com.hotelmanagement.enums.BedType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomTypeRequestDTO {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Integer capacity;

    @NotNull
    private BedType bedType;

}