package org.example.bilabonnement.service;



import org.example.bilabonnement.model.User;
import org.example.bilabonnement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private Map<String, User> cachedUserMap;

    public Map<String, User> getUserMap() {
        if (cachedUserMap == null) {
            cachedUserMap = new HashMap<>();
            for (User user : fetchAllUsersAsList()) {
                cachedUserMap.put(user.getUsername(), user);
            }
        }
        return cachedUserMap;
    }

    public ArrayList<User> fetchAllUsersAsList() {
        return new ArrayList<>(userRepository.fetchAllUsersAsList());
    }

    public User findByUsernameAndPassword(String username, String password) {
        try {
            return userRepository.findByUsernameAndPassword(username, password);
        } catch (Exception e) {
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

     forudsætter at navnet indeholder mindst to bogstaver
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
