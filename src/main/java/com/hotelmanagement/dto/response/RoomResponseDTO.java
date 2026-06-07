package com.hotelmanagement.dto.response;

import com.hotelmanagement.enums.RoomStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDTO {

    private Long id;

    private Long hotelId;

    private Long roomTypeId;

    private String roomNumber;

    private Integer floorNumber;

    private RoomStatus status;

}