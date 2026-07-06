package com.hotelmanagement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponseDTO {



    private Long totalHotels;

    private Long approvedHotels;

    private Long pendingHotels;

    private Long rejectedHotels;



    private Long totalUsers;

    private Long totalCustomers;

    private Long totalHotelAdmins;

    private Long totalSuperAdmins;

    private Long activeUsers;

    private Long blockedUsers;


    private Long totalRooms;

    private Long availableRooms;

    private Long occupiedRooms;

    private Long maintenanceRooms;

    private Long inactiveRooms;



    private Long totalBookings;

    private Long pendingBookings;

    private Long confirmedBookings;

    private Long checkedInBookings;

    private Long completedBookings;

    private Long cancelledBookings;



    private Long totalReviews;

    private Double averagePlatformRating;

}