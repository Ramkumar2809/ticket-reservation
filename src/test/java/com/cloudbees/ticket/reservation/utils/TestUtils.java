package com.cloudbees.ticket.reservation.utils;

import com.cloudbees.ticket.reservation.dto.BookingDTO;
import com.cloudbees.ticket.reservation.dto.ReceiptDTO;
import com.cloudbees.ticket.reservation.model.Passenger;
import com.cloudbees.ticket.reservation.model.Seat;
import com.cloudbees.ticket.reservation.model.Section;
import com.cloudbees.ticket.reservation.model.Train;
import com.cloudbees.ticket.reservation.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {
    private TestUtils() {}

    public static User getUser () {
        User user = new User(1L,"Test","User","testuser@gmail.com");
        return user;
    }

    public static Train getTrain() {
        Train train = new Train(1L,"From","To");
        List<Section> sections = List.of(buildSection(1L, "A"));
        train.setSections(sections);
        return train;
    }

    public static Section buildSection(Long id, String name) {
        Section section = new Section(id,name);
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1L));
        seats.add(new Seat(2L));
        seats.add(new Seat(3L));
        seats.add(new Seat(4L));
        seats.add(new Seat(5L));
        section.setSeats(seats);
        return section;
    }

    public static BookingDTO getBookingDTO() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setLastName("User");
        bookingDTO.setFirstName("Test");
        bookingDTO.setFrom("London");
        bookingDTO.setTo("France");
        bookingDTO.setEmail("testuser@gmail.com");
        return bookingDTO;
    }

    public static ReceiptDTO getReceiptDTO() {
        return new ReceiptDTO("Test user","From","To",20d,"A",1L);
    }

    public static List<Seat> getSeats() {
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat();
        seat.setSeatNo(1L);
        seat.setPassenger(new Passenger(TestUtils.getUser(),"From","To",20.d));
        seats.add(seat);
        return seats;
    }
}
