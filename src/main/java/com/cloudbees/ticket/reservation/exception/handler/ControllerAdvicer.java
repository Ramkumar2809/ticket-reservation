package com.cloudbees.ticket.reservation.exception.handler;

import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.ConflictException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvicer {

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<HttpStatusResponse> handleUnprocessableEntityException(UnprocessableEntityException exception) {
        return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<HttpStatusResponse> handleConflictException(ConflictException exception) {
        return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.CONFLICT.value(), exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessServiceException.class)
    public ResponseEntity<HttpStatusResponse> handleBusinessServiceException(BusinessServiceException exception) {
        return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
