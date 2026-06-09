package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.CreatePaymentRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.PaymentResponseDTO;
import com.hotelmanagement.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService
    ) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>>
    createPayment(
            @Valid
            @RequestBody
            CreatePaymentRequestDTO request
    ) {

        PaymentResponseDTO response =
                paymentService.createPayment(
                        request
                );

        return ResponseEntity.ok(
                ApiResponseDTO.<PaymentResponseDTO>builder()
                        .success(true)
                        .message(
                                "Payment successful"
                        )
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/my-payments")
    public ResponseEntity<ApiResponseDTO<List<PaymentResponseDTO>>>
    getMyPayments() {

        List<PaymentResponseDTO> response =
                paymentService.getMyPayments();

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<PaymentResponseDTO>>builder()
                        .success(true)
                        .message(
                                "Payments fetched successfully"
                        )
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>>
    getPaymentById(
            @PathVariable Long paymentId
    ) {

        PaymentResponseDTO response =
                paymentService.getPaymentById(
                        paymentId
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<PaymentResponseDTO>builder()
                        .success(true)
                        .message(
                                "Payment fetched successfully"
                        )
                        .data(response)
                        .build()
        );

    }

}