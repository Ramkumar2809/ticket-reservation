package com.cloudbees.ticket.reservation.controller;

import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.ConflictException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.service.TicketService;
import com.cloudbees.ticket.reservation.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @Test
    void bookTicketExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException, ConflictException {
        Mockito.doNothing().when(ticketService).bookTicket(Mockito.any());
        ResponseEntity<HttpStatusResponse> response = ticketController.bookTicket(TestUtils.getBookingDTO());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void cancelTicketExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException {
        Mockito.doNothing().when(ticketService).cancelTicket(Mockito.anyString());
        ResponseEntity<HttpStatusResponse> response = ticketController.cancelTicket("testuser@gmail.com");
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
