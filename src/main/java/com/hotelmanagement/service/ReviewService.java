package com.hotelmanagement.service;

import com.hotelmanagement.dto.request.CreateReviewRequestDTO;
import com.hotelmanagement.dto.response.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO createReview(
            CreateReviewRequestDTO request
    );

    List<ReviewResponseDTO> getHotelReviews(
            Long hotelId
    );

}