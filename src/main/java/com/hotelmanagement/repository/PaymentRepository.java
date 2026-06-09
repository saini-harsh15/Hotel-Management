package com.hotelmanagement.repository;

import com.hotelmanagement.entity.PaymentEntity;
import com.hotelmanagement.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository
        extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByBookingCustomerId(
            Long customerId
    );

    boolean existsByBooking(
            BookingEntity booking
    );

}