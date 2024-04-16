package com.cloudbees.ticket.reservation.exception;

public class BusinessServiceException extends Exception {
    public BusinessServiceException(String message) {
        super(message);
    }

    public BusinessServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
