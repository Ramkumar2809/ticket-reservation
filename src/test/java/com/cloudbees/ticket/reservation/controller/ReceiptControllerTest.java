package com.cloudbees.ticket.reservation.controller;

import com.cloudbees.ticket.reservation.dto.HttpStatusResponse;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.service.ReceiptService;
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
public class ReceiptControllerTest {

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private ReceiptController receiptController;

    @Test
    void getReceiptByEmailExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException {
        Mockito.when(receiptService.getReceiptByEmail(Mockito.anyString())).thenReturn(TestUtils.getReceiptDTO());
        ResponseEntity<HttpStatusResponse> response = receiptController.getReceiptByEmail("testuser@gmail.com");
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }
}
