package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.CreateBookingRequestDTO;
import com.hotelmanagement.dto.response.BookingResponseDTO;
import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.RoomEntity;
import com.hotelmanagement.entity.RoomTypeEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.BookingStatus;
import com.hotelmanagement.enums.RoomStatus;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.exception.UnauthorizedOperationException;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.RoomRepository;
import com.hotelmanagement.repository.RoomTypeRepository;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.service.BookingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final RoomTypeRepository roomTypeRepository;

    private final RoomRepository roomRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, RoomTypeRepository roomTypeRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public BookingResponseDTO checkOutBooking(Long bookingId) {

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getStatus() != BookingStatus.CHECKED_IN) {

            throw new IllegalArgumentException("Only checked-in bookings can be checked out");

        }

        RoomEntity room = booking.getAssignedRoom();

        if (room == null) {

            throw new IllegalArgumentException("No room assigned to this booking");

        }

        booking.setStatus(BookingStatus.COMPLETED);

        room.setStatus(RoomStatus.AVAILABLE);

        BookingEntity updatedBooking = bookingRepository.save(booking);

        return BookingResponseDTO.builder().id(updatedBooking.getId()).roomTypeId(updatedBooking.getRoomType().getId()).customerId(updatedBooking.getCustomer().getId()).assignedRoomId(updatedBooking.getAssignedRoom().getId()).assignedRoomNumber(updatedBooking.getAssignedRoom().getRoomNumber()).checkInDate(updatedBooking.getCheckInDate()).checkOutDate(updatedBooking.getCheckOutDate()).guestCount(updatedBooking.getGuestCount()).totalAmount(updatedBooking.getTotalAmount()).status(updatedBooking.getStatus()).build();

    }

    @Override
    public BookingResponseDTO checkInBooking(Long bookingId) {

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {

            throw new IllegalArgumentException("Only confirmed bookings can be checked in");

        }

        booking.setStatus(BookingStatus.CHECKED_IN);

        RoomEntity room = booking.getAssignedRoom();

        room.setStatus(RoomStatus.OCCUPIED);

        BookingEntity updatedBooking = bookingRepository.save(booking);

        return BookingResponseDTO.builder().id(updatedBooking.getId()).roomTypeId(updatedBooking.getRoomType().getId()).customerId(updatedBooking.getCustomer().getId()).assignedRoomId(updatedBooking.getAssignedRoom().getId()).assignedRoomNumber(updatedBooking.getAssignedRoom().getRoomNumber()).checkInDate(updatedBooking.getCheckInDate()).checkOutDate(updatedBooking.getCheckOutDate()).guestCount(updatedBooking.getGuestCount()).totalAmount(updatedBooking.getTotalAmount()).status(updatedBooking.getStatus()).build();

    }

    @Override
    public BookingResponseDTO confirmBooking(Long bookingId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity customer = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getCustomer().getId().equals(customer.getId())) {

            throw new UnauthorizedOperationException("You cannot confirm this booking");

        }

        if (booking.getStatus() != BookingStatus.PENDING_PAYMENT) {

            throw new IllegalArgumentException("Only pending bookings can be confirmed");

        }

        booking.setStatus(BookingStatus.CONFIRMED);

        BookingEntity updatedBooking = bookingRepository.save(booking);

        return BookingResponseDTO.builder().id(updatedBooking.getId()).roomTypeId(updatedBooking.getRoomType().getId()).customerId(customer.getId()).assignedRoomId(updatedBooking.getAssignedRoom() != null ? updatedBooking.getAssignedRoom().getId() : null).assignedRoomNumber(updatedBooking.getAssignedRoom() != null ? updatedBooking.getAssignedRoom().getRoomNumber() : null).checkInDate(updatedBooking.getCheckInDate()).checkOutDate(updatedBooking.getCheckOutDate()).guestCount(updatedBooking.getGuestCount()).totalAmount(updatedBooking.getTotalAmount()).status(updatedBooking.getStatus()).build();

    }

    @Override
    public List<BookingResponseDTO> getMyBookings() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity customer = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return bookingRepository.findByCustomer(customer).stream().map(booking -> BookingResponseDTO.builder().id(booking.getId()).roomTypeId(booking.getRoomType().getId()).customerId(booking.getCustomer().getId()).checkInDate(booking.getCheckInDate()).checkOutDate(booking.getCheckOutDate()).guestCount(booking.getGuestCount()).totalAmount(booking.getTotalAmount()).assignedRoomId(booking.getAssignedRoom() != null ? booking.getAssignedRoom().getId() : null).assignedRoomNumber(booking.getAssignedRoom() != null ? booking.getAssignedRoom().getRoomNumber() : null).status(booking.getStatus()).build()).toList();

    }

    @Override
    public BookingResponseDTO createBooking(CreateBookingRequestDTO request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity customer = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RoomTypeEntity roomType = roomTypeRepository.findById(request.getRoomTypeId()).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));

        long activeBookings = bookingRepository.countOverlappingBookings(roomType, request.getCheckInDate(), request.getCheckOutDate(), List.of(BookingStatus.PENDING_PAYMENT, BookingStatus.CONFIRMED, BookingStatus.CHECKED_IN));

        long totalRooms = roomType.getRooms().size();

        if (activeBookings >= totalRooms) {

            throw new IllegalArgumentException("No rooms available for selected dates");

        }

        RoomEntity assignedRoom = null;

        List<RoomEntity> rooms = roomRepository.findByRoomType(roomType);

        for (RoomEntity room : rooms) {

            boolean roomAvailable = bookingRepository.findByAssignedRoomAndStatusIn(room, List.of(BookingStatus.PENDING_PAYMENT, BookingStatus.CONFIRMED, BookingStatus.CHECKED_IN)).stream().noneMatch(existingBooking ->

                    existingBooking.getCheckInDate().isBefore(request.getCheckOutDate())

                            &&

                            existingBooking.getCheckOutDate().isAfter(request.getCheckInDate())

            );

            if (roomAvailable) {

                assignedRoom = room;
                break;

            }

        }

        if (assignedRoom == null) {

            throw new IllegalArgumentException("No rooms available for selected dates");

        }

        if (request.getGuestCount() > roomType.getCapacity()) {

            throw new IllegalArgumentException("Guest count exceeds room capacity");

        }

        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {

            throw new IllegalArgumentException("Check-out date must be after check-in date");

        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());

        BigDecimal totalAmount = roomType.getPrice().multiply(BigDecimal.valueOf(nights));

        BookingEntity booking = BookingEntity.builder().customer(customer).roomType(roomType).assignedRoom(assignedRoom).checkInDate(request.getCheckInDate()).checkOutDate(request.getCheckOutDate()).guestCount(request.getGuestCount()).pricePerNight(roomType.getPrice()).totalAmount(totalAmount).specialRequest(request.getSpecialRequest()).status(BookingStatus.PENDING_PAYMENT).build();
        BookingEntity savedBooking = bookingRepository.save(booking);

        return BookingResponseDTO.builder().id(savedBooking.getId()).roomTypeId(roomType.getId()).customerId(customer.getId()).checkInDate(savedBooking.getCheckInDate()).checkOutDate(savedBooking.getCheckOutDate()).guestCount(savedBooking.getGuestCount()).totalAmount(savedBooking.getTotalAmount()).assignedRoomId(savedBooking.getAssignedRoom() != null ? savedBooking.getAssignedRoom().getId() : null).assignedRoomNumber(savedBooking.getAssignedRoom() != null ? savedBooking.getAssignedRoom().getRoomNumber() : null).status(savedBooking.getStatus()).build();


    }

    @Override
    public BookingResponseDTO cancelBooking(Long bookingId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity customer = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getCustomer().getId().equals(customer.getId())) {

            throw new UnauthorizedOperationException("You cannot cancel this booking");

        }

        if (booking.getStatus() == BookingStatus.CHECKED_IN) {

            throw new IllegalArgumentException("Checked-in booking cannot be cancelled");

        }

        if (booking.getStatus() == BookingStatus.COMPLETED) {

            throw new IllegalArgumentException("Completed booking cannot be cancelled");

        }

        booking.setStatus(BookingStatus.CANCELLED);

        BookingEntity updatedBooking = bookingRepository.save(booking);

        return BookingResponseDTO.builder().id(updatedBooking.getId()).roomTypeId(updatedBooking.getRoomType().getId()).customerId(customer.getId()).checkInDate(updatedBooking.getCheckInDate()).checkOutDate(updatedBooking.getCheckOutDate()).guestCount(updatedBooking.getGuestCount()).totalAmount(updatedBooking.getTotalAmount()).assignedRoomId(updatedBooking.getAssignedRoom() != null ? updatedBooking.getAssignedRoom().getId() : null).assignedRoomNumber(updatedBooking.getAssignedRoom() != null ? updatedBooking.getAssignedRoom().getRoomNumber() : null).status(updatedBooking.getStatus()).build();

    }

}