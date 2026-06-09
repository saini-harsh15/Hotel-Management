package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.response.DashboardResponseDTO;
import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.enums.BookingStatus;
import com.hotelmanagement.enums.RoomStatus;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.HotelRepository;
import com.hotelmanagement.repository.RoomRepository;
import com.hotelmanagement.service.DashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardServiceImpl
        implements DashboardService {

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;

    private final BookingRepository bookingRepository;

    public DashboardServiceImpl(
            HotelRepository hotelRepository,
            RoomRepository roomRepository,
            BookingRepository bookingRepository
    ) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public DashboardResponseDTO getHotelDashboard(
            Long hotelId
    ) {

        HotelEntity hotel =
                hotelRepository
                        .findById(hotelId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel not found"
                                )
                        );

        long totalRooms =
                roomRepository.countByHotel(
                        hotel
                );

        long availableRooms =
                roomRepository.countByHotelAndStatus(
                        hotel,
                        RoomStatus.AVAILABLE
                );

        long occupiedRooms =
                roomRepository.countByHotelAndStatus(
                        hotel,
                        RoomStatus.OCCUPIED
                );

        long totalBookings =
                bookingRepository.countByRoomTypeHotel(
                        hotel
                );

        long completedBookings =
                bookingRepository.countByRoomTypeHotelAndStatus(
                        hotel,
                        BookingStatus.COMPLETED
                );

        BigDecimal totalRevenue =
                bookingRepository.getTotalRevenue(
                        hotel,
                        BookingStatus.COMPLETED
                );

        return DashboardResponseDTO.builder()
                .totalRooms(totalRooms)
                .availableRooms(availableRooms)
                .occupiedRooms(occupiedRooms)
                .totalBookings(totalBookings)
                .completedBookings(completedBookings)
                .totalRevenue(totalRevenue)
                .averageRating(
                        hotel.getAverageRating()
                )
                .totalReviews(
                        hotel.getTotalReviews()
                )
                .build();

    }

}