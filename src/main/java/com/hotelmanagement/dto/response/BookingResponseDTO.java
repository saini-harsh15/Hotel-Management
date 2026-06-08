package com.hotelmanagement.dto.response;

import com.hotelmanagement.enums.BookingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {

    private Long id;

    private Long roomTypeId;

    private Long customerId;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer guestCount;

    private BigDecimal totalAmount;

    private BookingStatus status;

    private Long assignedRoomId;

    private String assignedRoomNumber;

}