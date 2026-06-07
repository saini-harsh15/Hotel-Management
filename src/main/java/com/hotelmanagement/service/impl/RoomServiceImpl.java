package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.CreateRoomRequestDTO;
import com.hotelmanagement.dto.response.RoomResponseDTO;
import com.hotelmanagement.entity.RoomEntity;
import com.hotelmanagement.entity.RoomTypeEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.exception.UnauthorizedOperationException;
import com.hotelmanagement.repository.RoomRepository;
import com.hotelmanagement.repository.RoomTypeRepository;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.service.RoomService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final UserRepository userRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoomResponseDTO getRoomById(Long roomId) {

        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        return RoomResponseDTO.builder().id(room.getId()).hotelId(room.getHotel().getId()).roomTypeId(room.getRoomType().getId()).roomNumber(room.getRoomNumber()).floorNumber(room.getFloorNumber()).status(room.getStatus()).build();

    }

    @Override
    public List<RoomResponseDTO> getRoomsByRoomType(Long roomTypeId) {

        RoomTypeEntity roomType = roomTypeRepository.findById(roomTypeId).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));

        return roomRepository.findByRoomType(roomType).stream().map(room -> RoomResponseDTO.builder().id(room.getId()).hotelId(room.getHotel().getId()).roomTypeId(roomType.getId()).roomNumber(room.getRoomNumber()).floorNumber(room.getFloorNumber()).status(room.getStatus()).build()).toList();

    }

    @Override
    public RoomResponseDTO createRoom(Long roomTypeId, CreateRoomRequestDTO request) {

        RoomTypeEntity roomType = roomTypeRepository.findById(roomTypeId).orElseThrow(() -> new ResourceNotFoundException("Room type not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!roomType.getHotel().getOwner().getId().equals(user.getId())) {

            throw new UnauthorizedOperationException("You do not own this hotel");

        }

        RoomEntity room = RoomEntity.builder().hotel(roomType.getHotel()).roomType(roomType).roomNumber(request.getRoomNumber()).floorNumber(request.getFloorNumber()).build();

        RoomEntity savedRoom = roomRepository.save(room);

        return RoomResponseDTO.builder().id(savedRoom.getId()).hotelId(savedRoom.getHotel().getId()).roomTypeId(savedRoom.getRoomType().getId()).roomNumber(savedRoom.getRoomNumber()).floorNumber(savedRoom.getFloorNumber()).status(savedRoom.getStatus()).build();

    }

}