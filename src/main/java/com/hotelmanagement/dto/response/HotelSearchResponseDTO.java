package com.hotelmanagement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelSearchResponseDTO {

    private Long id;

    private String name;

    private String city;

    private String state;

    private String country;

    private Double averageRating;

    private Integer totalReviews;

}