package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.dto.BookingDTO;
import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.ConflictException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.model.Passenger;
import com.cloudbees.ticket.reservation.model.Section;
import com.cloudbees.ticket.reservation.model.Train;
import com.cloudbees.ticket.reservation.service.SeatService;
import com.cloudbees.ticket.reservation.service.UserService;
import com.cloudbees.ticket.reservation.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private Train train;

    @Mock
    private UserService userService;

    @Mock
    private SeatService seatService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void checkWhetherTicketIsBookedExpectsSuccess() {
        Section section = TestUtils.buildSection(1L, "A");
        section.getSeats().get(0).setPassenger(new Passenger(TestUtils.getUser(), "From", "To", 1d));
        Mockito.when(train.getSections()).thenReturn(List.of(section));
        boolean isTicketBooked = ticketService.checkWhetherTicketIsBooked("testuser@gmail.com");
        assertTrue(isTicketBooked);
    }

    @Test
    void cancelTicketExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        Mockito.doNothing().when(userService).deleteUser(Mockito.anyString());
        Mockito.doNothing().when(seatService).removeAllocatedSeat(Mockito.anyString());
        ticketService.cancelTicket("testuser@gmail.com");
    }

    @Test
    void cancelTicketExpectsEmailAsNull() {
        assertThrows(UnprocessableEntityException.class, () -> ticketService.cancelTicket(null));
    }

    @Test
    void cancelTicketExpectsNoUserFound() {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(null);
        assertThrows(UnprocessableEntityException.class, () -> ticketService.cancelTicket("testuser@gmail.com"));
    }

    @Test
    void cancelTicketExpectsException() throws UnprocessableEntityException, BusinessServiceException {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        Mockito.doNothing().when(userService).deleteUser(Mockito.anyString());
        Mockito.doNothing().when(seatService).removeAllocatedSeat(Mockito.isNull());
        assertThrows(BusinessServiceException.class, () -> ticketService.cancelTicket("testuser@gmail.com"));
    }

    @Test
    void bookTicketExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException, ConflictException {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        Mockito.doNothing().when(seatService).allocateSeat(Mockito.any(), Mockito.anyBoolean());
        ticketService.bookTicket(TestUtils.getBookingDTO());
    }

    @Test
    void bookTicketExpectsSuccessForNewUser() throws UnprocessableEntityException, BusinessServiceException, ConflictException {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(TestUtils.getUser());
        Mockito.doNothing().when(seatService).allocateSeat(Mockito.any(), Mockito.anyBoolean());
        ticketService.bookTicket(TestUtils.getBookingDTO());
    }

    @Test
    void bookTicketExpectsTicketAsBooked() {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        Section section = TestUtils.buildSection(1L, "A");
        section.getSeats().get(0).setPassenger(new Passenger(TestUtils.getUser(), "From", "To", 1d));
        Mockito.when(train.getSections()).thenReturn(List.of(section));
        assertThrows(ConflictException.class, () -> ticketService.bookTicket(TestUtils.getBookingDTO()));
    }

    @Test
    void bookTicketExpectsException() {
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(TestUtils.getUser());
        Mockito.when(train.getSections()).thenReturn(null);
        assertThrows(BusinessServiceException.class, () -> ticketService.bookTicket(TestUtils.getBookingDTO()));
    }

    @Test
    void bookTicketExpectsBookingDTOAsNull() {
        assertThrows(UnprocessableEntityException.class, () -> ticketService.bookTicket(null));

        BookingDTO bookingDTO = TestUtils.getBookingDTO();
        bookingDTO.setFirstName(null);
        BookingDTO finalBookingDTO = bookingDTO;
        assertThrows(UnprocessableEntityException.class, () -> ticketService.bookTicket(finalBookingDTO));

        bookingDTO = TestUtils.getBookingDTO();
        bookingDTO.setEmail("");
        BookingDTO finalBookingDTO1 = bookingDTO;
        assertThrows(UnprocessableEntityException.class, () -> ticketService.bookTicket(finalBookingDTO1));

        bookingDTO = TestUtils.getBookingDTO();
        bookingDTO.setFrom(null);
        BookingDTO finalBookingDTO2 = bookingDTO;
        assertThrows(UnprocessableEntityException.class, () -> ticketService.bookTicket(finalBookingDTO2));

        bookingDTO = TestUtils.getBookingDTO();
        bookingDTO.setFrom("Paris");
        BookingDTO finalBookingDTO3 = bookingDTO;
        assertThrows(UnprocessableEntityException.class, () -> ticketService.bookTicket(finalBookingDTO3));

        bookingDTO = TestUtils.getBookingDTO();
        bookingDTO.setTo("Japan");
        BookingDTO finalBookingDTO4 = bookingDTO;
        assertThrows(UnprocessableEntityException.class, () -> ticketService.bookTicket(finalBookingDTO4));
    }
}
