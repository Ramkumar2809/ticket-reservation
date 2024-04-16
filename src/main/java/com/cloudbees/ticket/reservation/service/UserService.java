package com.cloudbees.ticket.reservation.service;

import com.cloudbees.ticket.reservation.dto.BookingDTO;
import com.cloudbees.ticket.reservation.model.User;

public interface UserService {

    /**
     * To get the user details by email
     *
     * @param email
     * @return {@link User}
     */
    User getUserByEmail(String email);

    /**
     * To create the user
     *
     * @param bookingDTO
     * @return {@link User}
     */
    User createUser(BookingDTO bookingDTO);

    /**
     * To delete the user
     *
     * @param email
     */
    void deleteUser(String email);
}
