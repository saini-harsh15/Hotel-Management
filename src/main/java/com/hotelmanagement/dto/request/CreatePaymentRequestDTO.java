package com.hotelmanagement.dto.request;

import com.hotelmanagement.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentRequestDTO {

    @NotNull
    private Long bookingId;

    @NotNull
    private PaymentMethod paymentMethod;

}