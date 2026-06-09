package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.CreatePaymentRequestDTO;
import com.hotelmanagement.dto.response.PaymentResponseDTO;
import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.PaymentEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.BookingStatus;
import com.hotelmanagement.enums.PaymentStatus;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.PaymentRepository;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.service.PaymentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            BookingRepository bookingRepository,
            UserRepository userRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaymentResponseDTO createPayment(
            CreatePaymentRequestDTO request
    ) {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        UserEntity customer =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        BookingEntity booking =
                bookingRepository
                        .findById(request.getBookingId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );

        if (!booking.getCustomer()
                .getId()
                .equals(customer.getId())) {

            throw new IllegalArgumentException(
                    "You cannot pay for this booking"
            );

        }

        if (booking.getStatus() != BookingStatus.PENDING_PAYMENT) {

            throw new IllegalArgumentException(
                    "Payment can only be made for pending bookings"
            );

        }

        if (paymentRepository.existsByBooking(
                booking
        )) {

            throw new IllegalArgumentException(
                    "Payment already exists for this booking"
            );

        }

        PaymentEntity payment =
                PaymentEntity.builder()
                        .booking(booking)
                        .amount(
                                booking.getTotalAmount()
                        )
                        .paymentMethod(
                                request.getPaymentMethod()
                        )
                        .status(
                                PaymentStatus.SUCCESS
                        )
                        .transactionId(
                                UUID.randomUUID().toString()
                        )
                        .paidAt(
                                LocalDateTime.now()
                        )
                        .build();

        PaymentEntity savedPayment =
                paymentRepository.save(
                        payment
                );

        booking.setStatus(
                BookingStatus.CONFIRMED
        );

        bookingRepository.save(
                booking
        );

        return PaymentResponseDTO.builder()
                .id(savedPayment.getId())
                .bookingId(
                        booking.getId()
                )
                .amount(
                        savedPayment.getAmount()
                )
                .status(
                        savedPayment.getStatus()
                )
                .paymentMethod(
                        savedPayment.getPaymentMethod()
                )
                .transactionId(
                        savedPayment.getTransactionId()
                )
                .paidAt(
                        savedPayment.getPaidAt()
                )
                .build();

    }

    @Override
    public List<PaymentResponseDTO> getMyPayments() {

        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        UserEntity customer =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return paymentRepository
                .findByBookingCustomerId(
                        customer.getId()
                )
                .stream()
                .map(payment ->
                        PaymentResponseDTO.builder()
                                .id(payment.getId())
                                .bookingId(
                                        payment.getBooking().getId()
                                )
                                .amount(
                                        payment.getAmount()
                                )
                                .status(
                                        payment.getStatus()
                                )
                                .paymentMethod(
                                        payment.getPaymentMethod()
                                )
                                .transactionId(
                                        payment.getTransactionId()
                                )
                                .paidAt(
                                        payment.getPaidAt()
                                )
                                .build()
                )
                .toList();

    }

    @Override
    public PaymentResponseDTO getPaymentById(
            Long paymentId
    ) {

        PaymentEntity payment =
                paymentRepository
                        .findById(paymentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Payment not found"
                                )
                        );

        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .bookingId(
                        payment.getBooking().getId()
                )
                .amount(
                        payment.getAmount()
                )
                .status(
                        payment.getStatus()
                )
                .paymentMethod(
                        payment.getPaymentMethod()
                )
                .transactionId(
                        payment.getTransactionId()
                )
                .paidAt(
                        payment.getPaidAt()
                )
                .build();

    }

}