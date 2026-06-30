package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.CreateBookingRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.BookingResponseDTO;
import com.hotelmanagement.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> createBooking(@Valid @RequestBody CreateBookingRequestDTO request) {

        BookingResponseDTO response = bookingService.createBooking(request);

        return ResponseEntity.ok(ApiResponseDTO.<BookingResponseDTO>builder().success(true).message("Booking created successfully").data(response).build());

    }

    @GetMapping("/my-bookings")
    public ResponseEntity<ApiResponseDTO<List<BookingResponseDTO>>> getMyBookings() {

        List<BookingResponseDTO> response = bookingService.getMyBookings();

        return ResponseEntity.ok(ApiResponseDTO.<List<BookingResponseDTO>>builder().success(true).message("Bookings fetched successfully").data(response).build());

    }

    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> confirmBooking(@PathVariable Long bookingId) {

        BookingResponseDTO response = bookingService.confirmBooking(bookingId);

        return ResponseEntity.ok(ApiResponseDTO.<BookingResponseDTO>builder().success(true).message("Booking confirmed successfully").data(response).build());

    }

    @PutMapping("/{bookingId}/check-in")
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> checkInBooking(@PathVariable Long bookingId) {

        BookingResponseDTO response = bookingService.checkInBooking(bookingId);

        return ResponseEntity.ok(ApiResponseDTO.<BookingResponseDTO>builder().success(true).message("Check-in successful").data(response).build());

    }

    @PutMapping("/{bookingId}/check-out")
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> checkOutBooking(@PathVariable Long bookingId) {

        BookingResponseDTO response = bookingService.checkOutBooking(bookingId);

        return ResponseEntity.ok(ApiResponseDTO.<BookingResponseDTO>builder().success(true).message("Check-out successful").data(response).build());

    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> cancelBooking(@PathVariable Long bookingId) {

        BookingResponseDTO response = bookingService.cancelBooking(bookingId);

        return ResponseEntity.ok(ApiResponseDTO.<BookingResponseDTO>builder().success(true).message("Booking cancelled successfully").data(response).build());

    }

}