package org.example.bilabonnement.service;

import org.example.bilabonnement.model.User;
import org.example.bilabonnement.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester UserService for at sikre korrekt generering af brugernavne og korrekt håndtering af input.
 * Formålet er at validere forretningslogik for unikke brugernavne og fejltilfælde.
 */
class UserServiceTest {

    private UserRepository userRepository = new UserRepository();
    private UserService userService = new UserService(userRepository);

    /**
     * Tester at der genereres det korrekte brugernavn, når der ikke findes eksisterende brugere.
     */
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

    /**
     * Tester at der genereres et unikt brugernavn, når et brugernavn allerede er i brug.
     */
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

        assertNotEquals("boje0001", username);
        assertEquals("boje0002", username);
    }

    /**
     * Tester at der kastes en IllegalArgumentException, hvis fornavn er null.
     */
    @Test
    void generateUsernameWithNullFirstName_ShouldThrowException() {
        assertThrows((IllegalArgumentException.class), () -> {
            userService.generateUsername(null, "Jensen");
        });
    }

}
