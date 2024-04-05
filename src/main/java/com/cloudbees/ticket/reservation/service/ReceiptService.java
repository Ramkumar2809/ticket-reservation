package com.cloudbees.ticket.reservation.service;

import com.cloudbees.ticket.reservation.dto.ReceiptDTO;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;

public interface ReceiptService {

    /**
     * To get the ticket details by user email
     *
     * @param email
     * @return {@link ReceiptDTO}
     * @throws BusinessServiceException
     * @throws UnprocessableEntityException
     */
    ReceiptDTO getReceiptByEmail(String email) throws BusinessServiceException, UnprocessableEntityException;
}
