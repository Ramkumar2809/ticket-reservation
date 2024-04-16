package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.dto.BookingDTO;
import com.cloudbees.ticket.reservation.model.User;
import com.cloudbees.ticket.reservation.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl(List<User> users) {
        this.users = users;
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream().filter(user -> Objects.equals(user.getEmail(), email)).findFirst().orElse(null);
    }

    @Override
    public User createUser(BookingDTO bookingDTO) {
        User user = new User(users.get(0).getId() + 1, bookingDTO.getFirstName(), bookingDTO.getLastName(), bookingDTO.getEmail());
        users.add(user);
        return user;
    }

    @Override
    public void deleteUser(String email) {
        users.removeIf(user -> Objects.equals(user.getEmail(),email));
    }
}
