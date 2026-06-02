package com.hotelmanagement.dto.response;

import com.hotelmanagement.enums.HotelStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponseDTO {

    private Long id;

    private String name;

    private String city;

    private String state;

    private String country;

    private HotelStatus status;

    private Double averageRating;

}