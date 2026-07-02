package com.hotelmanagement.dto.request;

import com.hotelmanagement.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRoomStatusRequestDTO {

    @NotNull
    private RoomStatus status;

}