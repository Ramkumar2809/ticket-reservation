package com.cloudbees.ticket.reservation.exception;

public class UnprocessableEntityException extends Exception {
    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
