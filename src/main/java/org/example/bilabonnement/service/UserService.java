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

    //username er key og User er value
    //Singleton lignende mønster, for ikke at genere mappet hver gang den kaldes
    //da List -> Map er O(n), men vi vil gerne kunne fange users via username
    //igennem da map.get(username) er O(1) og derfor hurtigere
    public Map<String, User> usersAsMap() {
        if (cachedUserMap == null) {
            cachedUserMap = fetchAllUsers().stream()
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
            return null;
        }
    }

    public User findByUsername(String username) {
        return usersAsMap().get(username);
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

    //vi autogenerer et username til alle users
    //fra de to første bogstaver i deres fornavn + de to
    //første bogstaver i deres efternavn
    //imens at username allerede findes i mappet af Users
    //så bliver 0000 ++
    public String generateUsername(String fname, String lname) {
        String fnamePart = fname.substring(0, 2);
        String lnamePart = lname.substring(0, 2);
        String base = (fnamePart + lnamePart).toLowerCase();

        int counter = 1;
        String username = String.format("%s%04d", base, counter);

        while (usersAsMap().containsKey(username)) {
            counter++;
            username = base + String.format("%04d", counter);
        }

        return username;
    }

}
