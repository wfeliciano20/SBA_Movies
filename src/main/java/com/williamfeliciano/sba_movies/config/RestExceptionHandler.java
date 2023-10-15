package com.williamfeliciano.sba_movies.config;

import com.williamfeliciano.sba_movies.dtos.ErrorDto;
import com.williamfeliciano.sba_movies.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(ex.getErrorCode()).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract validation errors and return a response with error messages
//        ErrorDto errorDto = ErrorDto.builder()
//                .errorCode(HttpStatus.BAD_REQUEST)
//                .message(ex.getBindingResult().getAllErrors().toString())
//                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getBindingResult().getAllErrors());
    }
}
