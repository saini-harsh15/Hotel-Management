package com.hotelmanagement.dto.response;

import com.hotelmanagement.enums.HotelStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private String description;

    private String addressLine1;

    private String contactNumber;

    private String email;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    private Integer totalReviews;

    private String ownerName;

    private LocalDateTime createdAt;

}