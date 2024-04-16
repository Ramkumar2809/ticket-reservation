package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.model.Passenger;
import com.cloudbees.ticket.reservation.model.Seat;
import com.cloudbees.ticket.reservation.model.Train;
import com.cloudbees.ticket.reservation.model.User;
import com.cloudbees.ticket.reservation.service.SeatService;
import com.cloudbees.ticket.reservation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private static final Logger LOGGER = LogManager.getLogger(SeatServiceImpl.class);

    private Train train;

    private UserService userService;

    public SeatServiceImpl(Train train,UserService userService) {
        this.train = train;
        this.userService = userService;
    }

    @Override
    public List<Seat> getSeatDetailsBySectionName(String sectionName) throws BusinessServiceException, UnprocessableEntityException {
        try {
            boolean isSectionExists = train.getSections().stream().anyMatch(section -> Objects.equals(section.getName(), sectionName));
            if (!isSectionExists) {
                throw new UnprocessableEntityException("Section doesn't exists");
            }
            return train.getSections().stream().filter(section -> Objects.equals(section.getName(), sectionName)).flatMap(section -> section.getSeats().stream()).collect(Collectors.toList());
        } catch (UnprocessableEntityException e) {
            LOGGER.info(e.getMessage());
            throw new UnprocessableEntityException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void allocateSeat(Passenger passenger, boolean isReallocate) throws UnprocessableEntityException {
        Seat availableSeat = train.getSections().stream().flatMap(section -> section.getSeats().stream()).filter(seat -> Objects.isNull(seat.getPassenger())).findFirst().orElse(null);
        if (Objects.nonNull(availableSeat)) {
            availableSeat.setPassenger(passenger);
            if (isReallocate) {
                Seat allocatedSeat = getAllocatedSeat(passenger.getUser().getEmail());
                allocatedSeat.setPassenger(null);
            }
        } else {
            throw new UnprocessableEntityException("Seats are filled");
        }
    }

    @Override
    public void removeAllocatedSeat(String email) throws UnprocessableEntityException {
        Seat allocatedSeat = getAllocatedSeat(email);
        if(Objects.isNull(allocatedSeat)) {
            throw new UnprocessableEntityException("User is not booked the ticket");
        }
        allocatedSeat.setPassenger(null);
    }

    @Override
    public void reAllocateSeat(String email) throws UnprocessableEntityException, BusinessServiceException {
        try {
            if (Objects.isNull(email) || email.trim().isEmpty()) {
                throw new UnprocessableEntityException("Email cannot be null or empty");
            }
            User user = userService.getUserByEmail(email);
            if (Objects.isNull(user)) {
                throw new UnprocessableEntityException("User not found");
            }
            Seat allocatedSeat = getAllocatedSeat(email);
            if(Objects.isNull(allocatedSeat)) {
                throw new UnprocessableEntityException("User is not booked the ticket");
            }
            allocateSeat(allocatedSeat.getPassenger(),true);
        } catch (UnprocessableEntityException e) {
            LOGGER.info(e.getMessage());
            throw new UnprocessableEntityException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }

    private Seat getAllocatedSeat(String email) {
        return train.getSections().stream().flatMap(section -> section.getSeats().stream()).filter(seat -> Objects.nonNull(seat.getPassenger()) && Objects.nonNull(seat.getPassenger().getUser()) && Objects.equals(seat.getPassenger().getUser().getEmail(), email)).findFirst().orElse(null);
    }
}
