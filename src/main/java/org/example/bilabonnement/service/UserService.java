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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**

     key = username
     value = User

     Lazy initialization mønster
     List -> Map er O(n)
     map.get(username) er O(1) og derfor hurtigere

    */

    public Map<String, User> getUserMap() {
        if (cachedUserMap == null) {
            cachedUserMap = fetchAllUsers()
                            .stream()
                            .collect(Collectors.toMap(User::getUsername, user -> user));
        }
        return cachedUserMap;
    }

    public List<User> fetchAllUsers() {
        return userRepository.fetchAllUsersAsList();
    }

    public User findByUsernameAndPassword(String username, String password) {
        try {
            return userRepository.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
            // Fanger alle exceptions for at undgå at kaste videre,
            return null;
        }
    }

    public User findByUsernameInMap(String username) {
        return getUserMap().get(username);
    }

    public void updatePassword(String username, String newPassword) {
        userRepository.updatePassword(username, newPassword);
        clearUserCache();
    }

    public void clearUserCache() {
        cachedUserMap = null;
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public void deleteUserByUsername(String username) {
        userRepository.deleteUserByUsername(username);
    }

    /**

     autogenerer et username til alle users
     fra de to første bogstaver i deres fornavn +
     de to første bogstaver i deres efternavn
     imens at username allerede findes i mappet af Users
     så bliver 0000 ++

     */

    public String generateUsername(String fname, String lname) {
        String fnamePart = fname.substring(0, 2);
        String lnamePart = lname.substring(0, 2);
        String base = (fnamePart + lnamePart).toLowerCase();

        int counter = 1;
        String username = String.format("%s%04d", base, counter);

        while (getUserMap().containsKey(username)) {
            counter++;
            username = base + String.format("%04d", counter);
        }

        return username;
    }

}
