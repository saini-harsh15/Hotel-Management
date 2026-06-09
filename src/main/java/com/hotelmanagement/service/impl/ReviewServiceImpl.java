package com.hotelmanagement.service.impl;

import com.hotelmanagement.dto.request.CreateReviewRequestDTO;
import com.hotelmanagement.dto.response.ReviewResponseDTO;
import com.hotelmanagement.entity.BookingEntity;
import com.hotelmanagement.entity.ReviewEntity;
import com.hotelmanagement.entity.UserEntity;
import com.hotelmanagement.enums.BookingStatus;
import com.hotelmanagement.exception.ResourceNotFoundException;
import com.hotelmanagement.repository.BookingRepository;
import com.hotelmanagement.repository.ReviewRepository;
import com.hotelmanagement.repository.UserRepository;
import com.hotelmanagement.service.ReviewService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl
        implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            BookingRepository bookingRepository,
            UserRepository userRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewResponseDTO createReview(
            CreateReviewRequestDTO request
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
                    "You cannot review this booking"
            );

        }

        if (booking.getStatus()
                != BookingStatus.COMPLETED) {

            throw new IllegalArgumentException(
                    "Only completed bookings can be reviewed"
            );

        }

        if (reviewRepository.existsByBooking(
                booking
        )) {

            throw new IllegalArgumentException(
                    "Review already exists"
            );

        }

        ReviewEntity review =
                ReviewEntity.builder()
                        .booking(booking)
                        .rating(request.getRating())
                        .comment(request.getComment())
                        .build();

        ReviewEntity savedReview =
                reviewRepository.save(
                        review
                );

        return ReviewResponseDTO.builder()
                .id(savedReview.getId())
                .bookingId(
                        booking.getId()
                )
                .rating(
                        savedReview.getRating()
                )
                .comment(
                        savedReview.getComment()
                )
                .build();

    }

    @Override
    public List<ReviewResponseDTO> getHotelReviews(
            Long hotelId
    ) {

        return reviewRepository
                .findByBookingRoomTypeHotelId(
                        hotelId
                )
                .stream()
                .map(review ->
                        ReviewResponseDTO.builder()
                                .id(review.getId())
                                .bookingId(
                                        review.getBooking().getId()
                                )
                                .rating(
                                        review.getRating()
                                )
                                .comment(
                                        review.getComment()
                                )
                                .build()
                )
                .toList();

    }

}