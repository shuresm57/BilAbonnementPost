package org.example.bilabonnement.service;



import org.example.bilabonnement.model.user.User;
import org.example.bilabonnement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private Map<String, User> cachedUserMap;

    //String = username
    public Map<String, User> userMap() {
        if (cachedUserMap == null) {
            cachedUserMap = userRepository.fetchAllUsers().stream()
                    .collect(Collectors.toMap(User::getUsername, user -> user));
        }
        return cachedUserMap;
    }

    public User findByUsernameAndPassword(String username, String password) {
        try {
            return userRepository.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        return userMap().get(username);
    }

    public void updatePassword(String username, String newPassword) {
        userRepository.updatePassword(username, newPassword);
        clearUserCache();
    }

    public void clearUserCache() {
        cachedUserMap = null;
    }
}
