package org.example.bilabonnement.service;



import org.example.bilabonnement.model.user.User;
import org.example.bilabonnement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> fetchAllUsers() {
        return userRepository.fetchAllUsers();
    }

}
