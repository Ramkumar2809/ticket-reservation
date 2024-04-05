package com.cloudbees.ticket.reservation;

import com.cloudbees.ticket.reservation.model.Seat;
import com.cloudbees.ticket.reservation.model.Section;
import com.cloudbees.ticket.reservation.model.Train;
import com.cloudbees.ticket.reservation.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SeedingApplicationDataConfiguration {

    @Bean
    public Train getTrainDetails() {
        Train train = new Train(12345L,"London","France");
        List<Section> sections = Arrays.asList(buildSection(1L,"A"),buildSection(2L,"B"));
        train.setSections(sections);
        return train;
    }

    @Bean
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L,"Ram","Kumar","ramkumar@gmail.com"));
        return users;
    }


    private Section buildSection(Long id,String name) {
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
}
