package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.user.BusinessUser;
import org.example.bilabonnement.model.user.DamageUser;
import org.example.bilabonnement.model.user.DataUser;
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
    public List<User> fetchAllUsers() {
        String sql = "SELECT * FROM user";

        return template.query(sql, (rs, rowNum) -> {
            String role = rs.getString("role");
            String fname = rs.getString("fname");
            String lname = rs.getString("lname");
            String name = fname + " " + lname;
            String username = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String phoneNo = rs.getString("phone");

            return switch (role) {
                case "DATA", "ADMIN" -> new DataUser(name, username, password, email, phoneNo);
                case "SKADE" -> new DamageUser(name, username, password, email, phoneNo);
                case "UDVIKLING" -> new BusinessUser(name, username, password, email, phoneNo);
                default -> throw new IllegalArgumentException("Ukendt brugertype: " + role);
            };
        });
}
}
