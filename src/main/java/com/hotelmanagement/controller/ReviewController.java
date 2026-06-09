package com.hotelmanagement.controller;

import com.hotelmanagement.dto.request.CreateReviewRequestDTO;
import com.hotelmanagement.dto.response.ApiResponseDTO;
import com.hotelmanagement.dto.response.ReviewResponseDTO;
import com.hotelmanagement.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(
            ReviewService reviewService
    ) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ReviewResponseDTO>>
    createReview(
            @Valid
            @RequestBody
            CreateReviewRequestDTO request
    ) {

        ReviewResponseDTO response =
                reviewService.createReview(
                        request
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<ReviewResponseDTO>builder()
                        .success(true)
                        .message(
                                "Review created successfully"
                        )
                        .data(response)
                        .build()
        );

    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<ApiResponseDTO<List<ReviewResponseDTO>>>
    getHotelReviews(
            @PathVariable Long hotelId
    ) {

        List<ReviewResponseDTO> response =
                reviewService.getHotelReviews(
                        hotelId
                );

        return ResponseEntity.ok(
                ApiResponseDTO
                        .<List<ReviewResponseDTO>>builder()
                        .success(true)
                        .message(
                                "Reviews fetched successfully"
                        )
                        .data(response)
                        .build()
        );

    }

}