package ru.itis.companionapp.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itis.companionapp.exceptions.*;
import ru.itis.companionapp.exceptions.responces.CityErrorResponse;
import ru.itis.companionapp.exceptions.responces.DriveErrorResponse;
import ru.itis.companionapp.exceptions.responces.ReviewErrorResponse;

@RestControllerAdvice
public class ExceptionHandlerRestController {

    Logger logger = LoggerFactory.getLogger(ExceptionHandlerRestController.class);

    @ExceptionHandler(DriveNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DriveErrorResponse handleDriverNotFoundException(DriveNotFoundException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return DriveErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ReviewNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReviewErrorResponse handleReviewNotCreatedException(ReviewNotCreatedException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return ReviewErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ReviewErrorResponse handleReviewNotFoundException(ReviewNotFoundException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return ReviewErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CityErrorResponse handleCityNotFoundException(CityNotFoundException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return CityErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ReviewNotByUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ReviewErrorResponse handleReviewNotFoundException(ReviewNotByUserException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return ReviewErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReviewErrorResponse handleHttpMessageNotReadableException() {
        logger.error(HttpMessageNotReadableException.class.getSimpleName());
        return ReviewErrorResponse.builder()
                .message("Wrong JSON format")
                .build();
    }

    @ExceptionHandler(ReviewUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ReviewErrorResponse handleReviewUpdateException(ReviewUpdateException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return ReviewErrorResponse.builder()
                .message(e.getMessage())
                .build();
    }
}
