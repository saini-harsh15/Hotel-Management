package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.CreateHotelRequestDTO;
import com.hotelmanagement.dto.request.UpdateHotelRequestDTO;
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
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final UserRepository userRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }


    private HotelResponseDTO mapToResponse(HotelEntity hotel) {

        return HotelResponseDTO.builder().id(hotel.getId()).name(hotel.getName()).description(hotel.getDescription()).city(hotel.getCity()).state(hotel.getState()).country(hotel.getCountry()).addressLine1(hotel.getAddressLine1()).contactNumber(hotel.getContactNumber()).email(hotel.getEmail()).checkInTime(hotel.getCheckInTime()).checkOutTime(hotel.getCheckOutTime()).totalReviews(hotel.getTotalReviews()).status(hotel.getStatus()).averageRating(hotel.getAverageRating()).build();

    }


    @Override
    public List<HotelResponseDTO> getAllApprovedHotels() {

        List<HotelEntity> hotels = hotelRepository.findByStatus(HotelStatus.APPROVED);

        return hotels.stream().map(this::mapToResponse).toList();
    }

    @Override
    public HotelResponseDTO approveHotel(Long hotelId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != UserRole.SUPER_ADMIN) {

            throw new UnauthorizedOperationException("Only SUPER_ADMIN can approve hotels");

        }

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        hotel.setStatus(HotelStatus.APPROVED);

        HotelEntity updatedHotel = hotelRepository.save(hotel);

        return mapToResponse(updatedHotel);
    }

    @Override
    public HotelResponseDTO updateHotel(Long hotelId, UpdateHotelRequestDTO request) {

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!hotel.getOwner().getEmail().equals(email)) {

            throw new UnauthorizedOperationException("You cannot update this hotel");

        }

        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setAddressLine1(request.getAddressLine1());
        hotel.setAddressLine2(request.getAddressLine2());
        hotel.setCity(request.getCity());
        hotel.setState(request.getState());
        hotel.setCountry(request.getCountry());
        hotel.setPostalCode(request.getPostalCode());
        hotel.setContactNumber(request.getContactNumber());
        hotel.setEmail(request.getEmail());

        hotel.setCheckInTime(request.getCheckInTime());
        hotel.setCheckOutTime(request.getCheckOutTime());

        HotelEntity updatedHotel = hotelRepository.save(hotel);

        return mapToResponse(updatedHotel);
    }

    @Override
    public HotelResponseDTO getHotelById(Long hotelId) {

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));

        return mapToResponse(hotel);

    }

    @Override
    public List<HotelResponseDTO> getMyHotels() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity owner = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<HotelEntity> hotels = hotelRepository.findByOwner(owner);

        return hotels.stream().map(this::mapToResponse).toList();
    }

    @Override
    public HotelResponseDTO createHotel(CreateHotelRequestDTO request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity owner = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (owner.getRole() != UserRole.HOTEL_ADMIN) {

            throw new UnauthorizedOperationException("Only HOTEL_ADMIN can create hotels");

        }

        HotelEntity hotel = HotelEntity.builder().name(request.getName()).description(request.getDescription()).addressLine1(request.getAddressLine1()).addressLine2(request.getAddressLine2()).city(request.getCity()).state(request.getState()).country(request.getCountry()).postalCode(request.getPostalCode()).contactNumber(request.getContactNumber()).email(request.getEmail()).checkInTime(request.getCheckInTime()).checkOutTime(request.getCheckOutTime()).owner(owner).status(HotelStatus.PENDING).build();

        HotelEntity savedHotel = hotelRepository.save(hotel);

        return mapToResponse(savedHotel);
    }

    @Override
    public List<HotelResponseDTO> searchHotelsByCity(String city) {

        List<HotelEntity> hotels = hotelRepository.findByStatusAndCityIgnoreCase(HotelStatus.APPROVED, city);

        return hotels.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<HotelResponseDTO> searchHotelsByRating(Double minRating) {

        List<HotelEntity> hotels = hotelRepository.findByStatusAndAverageRatingGreaterThanEqual(HotelStatus.APPROVED, minRating);

        return hotels.stream().map(this::mapToResponse).toList();
    }

}