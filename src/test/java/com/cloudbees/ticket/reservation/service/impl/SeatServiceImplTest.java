package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.exception.BusinessServiceException;
import com.cloudbees.ticket.reservation.exception.UnprocessableEntityException;
import com.cloudbees.ticket.reservation.model.Passenger;
import com.cloudbees.ticket.reservation.model.Seat;
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

@ExtendWith(MockitoExtension.class)
public class SeatServiceImplTest {

    @Mock
    private Train train;

    @Mock
    private UserService userService;

    @InjectMocks
    private SeatServiceImpl seatService;

    @Test
    void getSeatDetailsBySectionNameExpectsSuccess() throws UnprocessableEntityException, BusinessServiceException {
        Section section = TestUtils.buildSection(1L, "A");
        section.getSeats().get(0).setPassenger(new Passenger(TestUtils.getUser(), "From", "To", 1d));
        Mockito.when(train.getSections()).thenReturn(List.of(section));
        List<Seat> seats = seatService.getSeatDetailsBySectionName("A");
        assertEquals(5, seats.size());
    }

    @Test
    void getSeatDetailsBySectionNameExpectsSectionNotExists() {
        Section section = TestUtils.buildSection(1L, "A");
        section.getSeats().get(0).setPassenger(new Passenger(TestUtils.getUser(), "From", "To", 1d));
        Mockito.when(train.getSections()).thenReturn(List.of(section));
        assertThrows(UnprocessableEntityException.class, () -> seatService.getSeatDetailsBySectionName("B"));
    }

    @Test
    void getSeatDetailsBySectionNameExpectsException() {
        Mockito.when(train.getSections()).thenReturn(null);
        assertThrows(BusinessServiceException.class, () -> seatService.getSeatDetailsBySectionName("B"));
    }
}
