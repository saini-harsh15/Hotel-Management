package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.CreateBookingRequestDTO;
import com.hotelmanagement.dto.response.BookingResponseDTO;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(
            CreateBookingRequestDTO request
    );

    List<BookingResponseDTO> getMyBookings();

    BookingResponseDTO cancelBooking(
            Long bookingId
    );

    BookingResponseDTO confirmBooking(
            Long bookingId
    );

    BookingResponseDTO checkInBooking(
            Long bookingId
    );

    BookingResponseDTO checkOutBooking(
            Long bookingId
    );

    List<BookingResponseDTO>
    getBookingsForHotel(
            Long hotelId
    );
}