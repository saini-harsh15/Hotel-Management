package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.CreateRoomTypeRequestDTO;
import com.hotelmanagement.dto.response.RoomTypeResponseDTO;
import com.hotelmanagement.entity.HotelEntity;
import com.hotelmanagement.entity.RoomTypeEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.exception.UnauthorizedOperationException;
import com.hotelmanagement.repository.HotelRepository;
import com.hotelmanagement.repository.RoomTypeRepository;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.service.RoomTypeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeServiceImpl
        implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public RoomTypeServiceImpl(
            RoomTypeRepository roomTypeRepository,
            HotelRepository hotelRepository,
            UserRepository userRepository
    ) {
        this.roomTypeRepository = roomTypeRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RoomTypeResponseDTO>
    getRoomTypesByHotel(
            Long hotelId
    ) {

        HotelEntity hotel =
                hotelRepository.findById(hotelId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel not found"
                                )
                        );

        return roomTypeRepository
                .findByHotel(hotel)
                .stream()
                .map(roomType ->
                        RoomTypeResponseDTO.builder()
                                .id(roomType.getId())
                                .hotelId(hotel.getId())
                                .name(roomType.getName())
                                .description(roomType.getDescription())
                                .price(roomType.getPrice())
                                .capacity(roomType.getCapacity())
                                .bedType(roomType.getBedType())
                                .build()
                )
                .toList();

    }

    @Override
    public RoomTypeResponseDTO createRoomType(
            Long hotelId,
            CreateRoomTypeRequestDTO request
    ) {

        HotelEntity hotel =
                hotelRepository.findById(hotelId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Hotel not found"
                                )
                        );

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        UserEntity user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        if (!hotel.getOwner()
                .getId()
                .equals(user.getId())) {

            throw new UnauthorizedOperationException(
                    "You do not own this hotel"
            );

        }

        RoomTypeEntity roomType =
                RoomTypeEntity.builder()
                        .hotel(hotel)
                        .name(request.getName())
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .capacity(request.getCapacity())
                        .bedType(request.getBedType())
                        .build();

        RoomTypeEntity savedRoomType =
                roomTypeRepository.save(roomType);

        return RoomTypeResponseDTO.builder()
                .id(savedRoomType.getId())
                .hotelId(hotel.getId())
                .name(savedRoomType.getName())
                .description(savedRoomType.getDescription())
                .price(savedRoomType.getPrice())
                .capacity(savedRoomType.getCapacity())
                .bedType(savedRoomType.getBedType())
                .build();

    }

}