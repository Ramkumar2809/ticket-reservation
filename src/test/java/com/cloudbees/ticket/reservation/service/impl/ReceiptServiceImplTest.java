package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.dto.ReceiptDTO;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.model.Passenger;
import com.cloudbees.ticket.reservation.model.Section;
import com.cloudbees.ticket.reservation.model.Train;
import com.cloudbees.ticket.reservation.model.User;
import com.cloudbees.ticket.reservation.service.UserService;
import com.cloudbees.ticket.reservation.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceImplTest {

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Mock
    private UserService userService;

    @Mock
    private Train train;


    @Test
    void getReceiptByEmailExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException {
        Section section = TestUtils.buildSection(1L, "A");
        section.getSeats().get(0).setPassenger(new Passenger(TestUtils.getUser(), "From", "To", 1d));
        Mockito.when(train.getSections()).thenReturn(List.of(section));
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        ReceiptDTO receiptDTO = receiptService.getReceiptByEmail("testuser@gmail.com");
        assertNotNull(receiptDTO);
    }

    @Test
    void getReceiptByEmailExpectsNullAsResult() throws UnprocessableEntityException, BusinessServiceException {
        Section section = TestUtils.buildSection(1L, "A");
        Mockito.when(train.getSections()).thenReturn(List.of(section));
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        ReceiptDTO receiptDTO = receiptService.getReceiptByEmail("testuser@gmail.com");
        assertNull(receiptDTO);
    }

    @Test
    void getReceiptByEmailExpectsException() {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        Mockito.when(train.getSections()).thenReturn(null);
        assertThrows(BusinessServiceException.class, () -> receiptService.getReceiptByEmail("testuser@gmail.com"));
    }

    @Test
    void getReceiptByEmailExpectsEmailAsNull() {
        assertThrows(UnprocessableEntityException.class, () -> receiptService.getReceiptByEmail(null));
    }

    @Test
    void getReceiptByEmailExpectsNoUserFound() {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(null);
        assertThrows(UnprocessableEntityException.class, () -> receiptService.getReceiptByEmail("testuser@gmail.com"));
    }
}
