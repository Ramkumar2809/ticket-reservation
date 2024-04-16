package com.cloudbees.ticket.reservation.service.impl;

import com.cloudbees.ticket.reservation.model.User;
import com.cloudbees.ticket.reservation.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private List<User> users;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByEmailExpectsSuccess() {
        userService.getUserByEmail("testuser@gmail.com");
    }

    @Test
    void createUserExpectsSuccess() {
        Mockito.when(users.get(0)).thenReturn(TestUtils.getUser());
        userService.createUser(TestUtils.getBookingDTO());
    }

    @Test
    void deleteUserExpectsSuccess() {
        userService.deleteUser("testuser@gmail.com");
    }

}
