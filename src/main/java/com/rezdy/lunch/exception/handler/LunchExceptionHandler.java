package com.rezdy.lunch.exception.handler;

import com.rezdy.lunch.exception.InvalidIngredientsException;
import com.rezdy.lunch.exception.ResourceNotFoundException;
import com.rezdy.lunch.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class LunchExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(DateTimeParseException exception) {
        return new ResponseEntity<>(new ErrorResponse().setErrorMessage(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(new ErrorResponse().setErrorMessage(resourceNotFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidIngredientsException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(InvalidIngredientsException invalidIngredientsException) {
        return new ResponseEntity<>(new ErrorResponse().setErrorMessage(invalidIngredientsException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
