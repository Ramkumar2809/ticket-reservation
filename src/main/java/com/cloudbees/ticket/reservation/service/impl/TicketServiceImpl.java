package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.dto.BookingDTO;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.ConflictException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.model.Passenger;
import com.cloudbees.ticket.reservation.model.Train;
import com.cloudbees.ticket.reservation.model.User;
import com.cloudbees.ticket.reservation.service.SeatService;
import com.cloudbees.ticket.reservation.service.TicketService;
import com.cloudbees.ticket.reservation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LogManager.getLogger(TicketServiceImpl.class);

    private Train train;

    private UserService userService;

    private SeatService seatService;

    public TicketServiceImpl(Train train,UserService userService,SeatService seatService) {
        this.train = train;
        this.userService = userService;
        this.seatService = seatService;
    }

    @Override
    public boolean checkWhetherTicketIsBooked(String email) {
        return train.getSections().stream().flatMap(section -> section.getSeats().stream()).anyMatch(seat -> Objects.nonNull(seat.getPassenger()) && Objects.nonNull(seat.getPassenger().getUser()) && Objects.equals(seat.getPassenger().getUser().getEmail(), email));
    }

    @Override
    public void bookTicket(BookingDTO booking) throws UnprocessableEntityException, ConflictException, BusinessServiceException {
        try {
            validateBookingDetails(booking);
            User userDetails = userService.getUserByEmail(booking.getEmail());
            if (Objects.isNull(userDetails)) {
                userDetails = userService.createUser(booking);
            }
            boolean isTicketBooked = checkWhetherTicketIsBooked(booking.getEmail());
            if (!isTicketBooked) {
                seatService.allocateSeat(new Passenger(userDetails, booking.getFrom(), booking.getTo(), 20d),false);
            } else {
                throw new ConflictException("Ticket is already booked for this user");
            }
        } catch (UnprocessableEntityException e) {
            LOGGER.info(e.getMessage());
            throw new UnprocessableEntityException(e.getMessage());
        } catch (ConflictException e) {
            LOGGER.info(e.getMessage());
            throw new ConflictException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void cancelTicket(String email) throws UnprocessableEntityException, BusinessServiceException {
        try {
            if (Objects.isNull(email) || email.trim().isEmpty()) {
                throw new UnprocessableEntityException("Email cannot be null or empty");
            }
            User user = userService.getUserByEmail(email);
            if (Objects.isNull(user)) {
                throw new UnprocessableEntityException("User not found");
            }
            userService.deleteUser(email);
            seatService.removeAllocatedSeat(email);
        } catch (UnprocessableEntityException e) {
            LOGGER.info(e.getMessage());
            throw new UnprocessableEntityException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }

    private void validateBookingDetails(BookingDTO booking) throws UnprocessableEntityException {
        if (Objects.isNull(booking)) {
            throw new UnprocessableEntityException("Booking details cannot be null");
        }
        if (Objects.isNull(booking.getEmail()) || booking.getEmail().trim().isEmpty()) {
            throw new UnprocessableEntityException("Email is mandatory");
        }
        if (Objects.isNull(booking.getFirstName()) || Objects.isNull(booking.getLastName()) || booking.getFirstName().trim().isEmpty() || booking.getLastName().trim().isEmpty()) {
            throw new UnprocessableEntityException("First name and last name is mandatory");
        }
        if (Objects.isNull(booking.getFrom()) || Objects.isNull(booking.getTo()) || booking.getFrom().trim().isEmpty() || booking.getTo().trim().isEmpty()) {
            throw new UnprocessableEntityException("From and To details cannot be empty");
        }
        if (!booking.getFrom().equalsIgnoreCase("London")) {
            throw new UnprocessableEntityException("From station is not valid. Supported values are: London");
        }
        if (!booking.getTo().equalsIgnoreCase("France")) {
            throw new UnprocessableEntityException("To station is not valid. Supported values are: France");
        }
    }
}
