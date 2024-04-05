package com.cloudbees.ticket.reservation.service;

import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.model.Passenger;
import com.cloudbees.ticket.reservation.model.Seat;

import java.util.List;

public interface SeatService {

    /**
     * To get the seat and allocation details by section name
     *
     * @param sectionName
     * @return List of {@link Seat}
     * @throws BusinessServiceException
     * @throws UnprocessableEntityException
     */
    List<Seat> getSeatDetailsBySectionName(String sectionName) throws BusinessServiceException, UnprocessableEntityException;

    /**
     * To allocate the seat for the user
     *
     * @param passenger
     * @param isReAllocate
     * @throws UnprocessableEntityException
     */
    void allocateSeat(Passenger passenger, boolean isReAllocate) throws UnprocessableEntityException;

    /**
     * To remove the allocated seat
     *
     * @param email
     * @throws UnprocessableEntityException
     */
    void removeAllocatedSeat(String email) throws UnprocessableEntityException;

    /**
     * To reallocate the seat for the user
     *
     * @param email
     * @throws UnprocessableEntityException
     * @throws BusinessServiceException
     */
    void reAllocateSeat(String email) throws UnprocessableEntityException, BusinessServiceException;
}
