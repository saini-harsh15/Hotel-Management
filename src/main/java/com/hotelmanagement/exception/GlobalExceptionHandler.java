package com.hotelmanagement.exception;

import com.hotelmanagement.dto.response.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<Object>>
    handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex
    ) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ApiResponseDTO.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .build()
                );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>>
    handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponseDTO.<Map<String, String>>builder()
                                .success(false)
                                .message("Validation failed")
                                .data(errors)
                                .build()
                );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>>
    handleGenericException(
            Exception ex
    ) {

        return ResponseEntity.status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(
                        ApiResponseDTO.builder()
                                .success(false)
                                .message("Something went wrong")
                                .data(null)
                                .build()
                );

    }

    @ExceptionHandler(
            InvalidCredentialsException.class
    )
    public ResponseEntity<ApiResponseDTO<Object>>
    handleInvalidCredentialsException(
            InvalidCredentialsException ex
    ) {

        return ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                )
                .body(
                        ApiResponseDTO.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .build()
                );

    }

    @ExceptionHandler(
            UnauthorizedOperationException.class
    )
    public ResponseEntity<
            ApiResponseDTO<Object>
            > handleUnauthorizedOperation(
            UnauthorizedOperationException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ApiResponseDTO.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .build()
                );

    }
}