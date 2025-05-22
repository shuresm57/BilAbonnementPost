package org.example.bilabonnement.service;

import org.example.bilabonnement.model.User;
import org.example.bilabonnement.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



class UserServiceTest {


    private UserRepository userRepository = new UserRepository();
    private UserService userService = new UserService(userRepository);



    @Test
    void generateUsernameOne() {
        UserRepository fakeRepo = new UserRepository() {
            @Override
            public List<User> fetchAllUsersAsList() {
                return List.of();
            }
        };
        UserService svc = new UserService(fakeRepo);
        String username = svc.generateUsername("Bo", "Jensen");

        assertEquals("boje0001", username);
    }

    @Test
    void generateUsernameTwo() {
        UserRepository fakeRepo = new UserRepository() {
            @Override
            public List<User> fetchAllUsersAsList() {
                User existing = new User();
                existing.setUsername("boje0001");
                return List.of(existing);
            }
        };

        UserService svc = new UserService(fakeRepo);
        String username = svc.generateUsername("Bo", "Jessen");

        assertEquals("boje0002", username);
    }



}