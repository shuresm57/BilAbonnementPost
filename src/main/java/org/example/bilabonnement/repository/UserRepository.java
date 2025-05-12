package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate template;

    //Vi bruger datatypen Set, så vi er sikre på at der ikke er nogen duplikater
    public Set<User> fetchAllUsers() {
        String sql = "SELECT * FROM user";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        List<User> users = template.query(sql, rowMapper);

        //Sorterer listen via username
        Set<User> sortedUsers = new TreeSet<>(Comparator.comparing(User::getUsername));
        sortedUsers.addAll(users);
        return sortedUsers;
    }
}
