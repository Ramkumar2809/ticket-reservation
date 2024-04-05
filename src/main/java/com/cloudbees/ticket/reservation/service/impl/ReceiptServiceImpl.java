package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.dto.ReceiptDTO;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.model.Seat;
import com.cloudbees.ticket.reservation.model.Section;
import com.cloudbees.ticket.reservation.model.Train;
import com.cloudbees.ticket.reservation.model.User;
import com.cloudbees.ticket.reservation.service.ReceiptService;
import com.cloudbees.ticket.reservation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger LOGGER = LogManager.getLogger(ReceiptServiceImpl.class);

    private Train train;

    private UserService userService;

    public ReceiptServiceImpl(Train train, UserService userService) {
        this.train = train;
        this.userService = userService;
    }

    @Override
    public ReceiptDTO getReceiptByEmail(String email) throws BusinessServiceException, UnprocessableEntityException {
        try {
            if (Objects.isNull(email) || email.trim().isEmpty()) {
                throw new UnprocessableEntityException("Email cannot be null or empty");
            }
            User user = userService.getUserByEmail(email);
            if (Objects.isNull(user)) {
                throw new UnprocessableEntityException("User not found");
            }
            Map<String, List<Seat>> seatsBySectionName = train.getSections().stream().collect(Collectors.groupingBy(Section::getName, Collectors.flatMapping(section -> section.getSeats().stream(), Collectors.toList())));
            for (Map.Entry<String, List<Seat>> seatsEntry : seatsBySectionName.entrySet()) {
                Seat allocatedSeatForUser = seatsEntry.getValue().stream().filter(seat -> Objects.nonNull(seat.getPassenger()) && Objects.nonNull(seat.getPassenger().getUser()) && Objects.equals(seat.getPassenger().getUser().getEmail(), email)).findFirst().orElse(null);
                if (Objects.nonNull(allocatedSeatForUser)) {
                    return buildReceipt(seatsEntry.getKey(), allocatedSeatForUser);
                }
            }
        } catch (UnprocessableEntityException e) {
            LOGGER.info(e.getMessage());
            throw new UnprocessableEntityException(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessServiceException(e.getMessage(), e);
        }
        return null;
    }

    private ReceiptDTO buildReceipt(String sectionName, Seat allocatedSeatForUser) {
        ReceiptDTO receipt = new ReceiptDTO();
        User userDetails = allocatedSeatForUser.getPassenger().getUser();
        receipt.setUser(userDetails.getFirstName() + " " + userDetails.getLastName());
        receipt.setFrom(allocatedSeatForUser.getPassenger().getFromStation());
        receipt.setTo(allocatedSeatForUser.getPassenger().getToStation());
        receipt.setFare(allocatedSeatForUser.getPassenger().getFare());
        receipt.setSection(sectionName);
        receipt.setSeatNo(allocatedSeatForUser.getSeatNo());
        return receipt;
    }
}
