package com.cloudbees.ticket.reservation.service;

import com.cloudbees.ticket.reservation.dto.BookingDTO;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.ConflictException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;

public interface TicketService {

    /**
     * To check whether the seat allocated for the user
     *
     * @param email
     * @return {@link boolean}
     */
    boolean checkWhetherTicketIsBooked(String email);

    /**
     * To book the ticket
     *
     * @param booking
     * @throws UnprocessableEntityException
     * @throws ConflictException
     * @throws BusinessServiceException
     */
    void bookTicket(BookingDTO booking) throws UnprocessableEntityException, ConflictException, BusinessServiceException;

    /**
     * To cancel the ticket
     *
     * @param email
     * @throws UnprocessableEntityException
     * @throws BusinessServiceException
     */
    void cancelTicket(String email) throws UnprocessableEntityException, BusinessServiceException;
}
