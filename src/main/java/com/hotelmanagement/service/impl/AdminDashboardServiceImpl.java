package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.response.AdminDashboardResponseDTO;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.*;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.exception.UnauthorizedOperationException;
import com.hotelmanagement.repository.*;
import com.hotelmanagement.service.AdminDashboardService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardServiceImpl
        implements AdminDashboardService {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public AdminDashboardServiceImpl(
            HotelRepository hotelRepository,
            UserRepository userRepository,
            RoomRepository roomRepository,
            BookingRepository bookingRepository,
            ReviewRepository reviewRepository
    ) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public AdminDashboardResponseDTO getDashboard() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        UserEntity user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        if (user.getRole() != UserRole.SUPER_ADMIN) {

            throw new UnauthorizedOperationException(
                    "Only SUPER_ADMIN can access dashboard analytics."
            );

        }

        return AdminDashboardResponseDTO.builder()

                // Hotels

                .totalHotels(
                        hotelRepository.count()
                )

                .approvedHotels(
                        hotelRepository.countByStatus(
                                HotelStatus.APPROVED
                        )
                )

                .pendingHotels(
                        hotelRepository.countByStatus(
                                HotelStatus.PENDING
                        )
                )

                .rejectedHotels(
                        hotelRepository.countByStatus(
                                HotelStatus.REJECTED
                        )
                )

                // Users

                .totalUsers(
                        userRepository.count()
                )

                .totalCustomers(
                        userRepository.countByRole(
                                UserRole.CUSTOMER
                        )
                )

                .totalHotelAdmins(
                        userRepository.countByRole(
                                UserRole.HOTEL_ADMIN
                        )
                )

                .totalSuperAdmins(
                        userRepository.countByRole(
                                UserRole.SUPER_ADMIN
                        )
                )

                .activeUsers(
                        userRepository.countByStatus(
                                UserStatus.ACTIVE
                        )
                )

                .blockedUsers(
                        userRepository.countByStatus(
                                UserStatus.BLOCKED
                        )
                )

                // Rooms

                .totalRooms(
                        roomRepository.count()
                )

                .availableRooms(
                        roomRepository.countByStatus(
                                RoomStatus.AVAILABLE
                        )
                )

                .occupiedRooms(
                        roomRepository.countByStatus(
                                RoomStatus.OCCUPIED
                        )
                )

                .maintenanceRooms(
                        roomRepository.countByStatus(
                                RoomStatus.MAINTENANCE
                        )
                )

                .inactiveRooms(
                        roomRepository.countByStatus(
                                RoomStatus.INACTIVE
                        )
                )

                // Bookings

                .totalBookings(
                        bookingRepository.count()
                )

                .pendingBookings(
                        bookingRepository.countByStatus(
                                BookingStatus.PENDING_PAYMENT
                        )
                )

                .confirmedBookings(
                        bookingRepository.countByStatus(
                                BookingStatus.CONFIRMED
                        )
                )

                .checkedInBookings(
                        bookingRepository.countByStatus(
                                BookingStatus.CHECKED_IN
                        )
                )

                .completedBookings(
                        bookingRepository.countByStatus(
                                BookingStatus.COMPLETED
                        )
                )

                .cancelledBookings(
                        bookingRepository.countByStatus(
                                BookingStatus.CANCELLED
                        )
                )

                // Reviews

                .totalReviews(
                        reviewRepository.count()
                )

                .averagePlatformRating(
                        reviewRepository.getAveragePlatformRating()
                )

                .build();

    }

}