package com.cloudbees.ticket.reservation.controller;

import com.cloudbees.ticket.reservation.dto.BookingDTO;
import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.ConflictException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.service.TicketService;
import com.cloudbees.ticket.reservation.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(value = "/book")
    public ResponseEntity<HttpStatusResponse> bookTicket(@RequestBody BookingDTO booking) throws UnprocessableEntityException, BusinessServiceException, ConflictException {
        ticketService.bookTicket(booking);
        return ResponseUtils.prepareSuccessResponse(null,"Ticket Booked Successfully");
    }

    @DeleteMapping(value = "/cancel")
    public ResponseEntity<HttpStatusResponse> cancelTicket(@RequestParam(value = "email", required = true) String email) throws UnprocessableEntityException, BusinessServiceException {
        ticketService.cancelTicket(email);
        return ResponseUtils.prepareSuccessResponse(null,"Ticket cancelled successfully");
    }
}
