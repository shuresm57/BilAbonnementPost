package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.user.BusinessUser;
import org.example.bilabonnement.model.user.DamageUser;
import org.example.bilabonnement.model.user.DataUser;
import org.example.bilabonnement.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate template;

    private User mapRowToUser(ResultSet rs) {
        try {
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
                default -> throw new IllegalArgumentException("Ukendt rolle: " + role);
            };
        } catch (Exception e) {
            throw new RuntimeException("Fejl ved mapping af bruger", e);
        }
    }

    public List<User> fetchAllUsersAsList() {
        String sql = """
        SELECT * FROM user ORDER BY username
        """;
        return template.query(sql, (rs, rowNum) -> mapRowToUser(rs));
    }

    public User findByUsernameAndPassword(String username, String password) {
        String sql = """
        SELECT * FROM user WHERE username = ? AND password = MD5(?)
        """;
        return template.queryForObject(sql, (rs, rowNum) -> mapRowToUser(rs), username, password);
    }

    public void addUser(User user) {
        String sql = """
        INSERT INTO user (navn, brugernavn, email, number, password, role)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        template.update(sql,
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNo(),
                user.getPassword(),
                user.getRole()
        );
    }

    public User findUser(String fullName) {
        String sql = """
        SELECT * FROM user WHERE CONCAT(fname, ' ', lname) LIKE ?
        """;
        return template.queryForObject(sql, (rs, rowNum) -> mapRowToUser(rs), "%" + fullName + "%");
    }

    public void deleteUser(User user) {
        String sql = """
        DELETE FROM user WHERE username = ?
        """;
        template.update(sql, user.getUsername());
    }

    public void updateUser(User user) {
        String sql = """
        UPDATE user
        SET fname = ?, lname = ?, password = MD5('?'), email = ?, phone = ?, role = ?
        WHERE username = ?
    """;

        String[] names = user.getName().split(" ", 2);
        String fname = names.length > 0 ? names[0] : "";
        String lname = names.length > 1 ? names[1] : "";

        template.update(sql,
                fname,
                lname,
                user.getPassword(),
                user.getEmail(),
                user.getPhoneNo(),
                user.getRole(),
                user.getUsername()
        );
    }

    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE user SET password = MD5(?) WHERE username = ?";
        template.update(sql, newPassword, username);
    }
}