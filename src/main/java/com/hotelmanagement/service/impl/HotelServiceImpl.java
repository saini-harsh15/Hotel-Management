package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.CreateHotelRequestDTO;
import com.hotelmanagement.dto.response.HotelResponseDTO;
import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.HotelStatus;
import com.hotelmanagement.enums.UserRole;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.exception.UnauthorizedOperationException;
import com.hotelmanagement.repository.HotelRepository;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.service.HotelService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl
        implements HotelService {

    private final HotelRepository hotelRepository;

    private final UserRepository userRepository;

    public HotelServiceImpl(
            HotelRepository hotelRepository,
            UserRepository userRepository
    ) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<HotelResponseDTO> getMyHotels() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        UserEntity owner =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        List<HotelEntity> hotels =
                hotelRepository.findByOwner(owner);

        return hotels.stream()
                .map(hotel ->
                        HotelResponseDTO.builder()
                                .id(hotel.getId())
                                .name(hotel.getName())
                                .city(hotel.getCity())
                                .state(hotel.getState())
                                .country(hotel.getCountry())
                                .status(hotel.getStatus())
                                .averageRating(
                                        hotel.getAverageRating()
                                )
                                .build()
                )
                .toList();

    }

    @Override
    public HotelResponseDTO createHotel(
            CreateHotelRequestDTO request
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        UserEntity owner =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        if (owner.getRole() != UserRole.HOTEL_ADMIN) {

            throw new UnauthorizedOperationException(
                    "Only HOTEL_ADMIN can create hotels"
            );

        }

        HotelEntity hotel =
                HotelEntity.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .addressLine1(request.getAddressLine1())
                        .addressLine2(request.getAddressLine2())
                        .city(request.getCity())
                        .state(request.getState())
                        .country(request.getCountry())
                        .postalCode(request.getPostalCode())
                        .contactNumber(request.getContactNumber())
                        .email(request.getEmail())
                        .checkInTime(request.getCheckInTime())
                        .checkOutTime(request.getCheckOutTime())
                        .owner(owner)
                        .status(HotelStatus.PENDING)
                        .build();

        HotelEntity savedHotel =
                hotelRepository.save(hotel);

        return HotelResponseDTO.builder()
                .id(savedHotel.getId())
                .name(savedHotel.getName())
                .city(savedHotel.getCity())
                .state(savedHotel.getState())
                .country(savedHotel.getCountry())
                .status(savedHotel.getStatus())
                .averageRating(savedHotel.getAverageRating())
                .build();

    }

}