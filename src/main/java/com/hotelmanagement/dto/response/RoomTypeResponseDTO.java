package com.hotelmanagement.dto.response;

import com.hotelmanagement.enums.BedType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomTypeResponseDTO {

    private Long id;

    private Long hotelId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer capacity;

    private BedType bedType;

}