package com.hotelmanagement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {

    private Long id;

    private Long bookingId;

    private String customerName;

    private String customerEmail;

    private Integer rating;

    private String comment;

}