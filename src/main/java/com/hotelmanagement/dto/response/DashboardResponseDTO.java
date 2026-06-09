package com.hotelmanagement.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {

    private Long totalRooms;

    private Long availableRooms;

    private Long occupiedRooms;

    private Long totalBookings;

    private Long completedBookings;

    private BigDecimal totalRevenue;

    private Double averageRating;

    private Integer totalReviews;

}