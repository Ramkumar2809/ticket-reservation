package com.cloudbees.ticket.reservation.controller;

import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.service.SeatService;
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
public class SeatControllerTest {

    @Mock
    private SeatService seatService;

    @InjectMocks
    private SeatController seatController;

    @Test
    void getSeatDetailsBySectionNameExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException {
        Mockito.when(seatService.getSeatDetailsBySectionName(Mockito.anyString())).thenReturn(TestUtils.getSeats());
        ResponseEntity<HttpStatusResponse> response = seatController.getSeatDetailsBySectionName("A");
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void reAllocateSeatExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException {
        Mockito.doNothing().when(seatService).reAllocateSeat(Mockito.anyString());
        ResponseEntity<HttpStatusResponse> response = seatController.reAllocateSeat("testuser@gmail.com");
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
