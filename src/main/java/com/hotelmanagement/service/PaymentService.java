package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.CreatePaymentRequestDTO;
import com.hotelmanagement.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    PaymentResponseDTO createPayment(
            CreatePaymentRequestDTO request
    );

    List<PaymentResponseDTO> getMyPayments();

    PaymentResponseDTO getPaymentById(
            Long paymentId
    );

}