package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository{

    @Autowired
    JdbcTemplate template;

    public List<User> fetchAllUsersAsList() {
        String sql = "SELECT * FROM user ORDER BY username";

        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return template.query(sql, rowMapper);
    }

    public User findByUsernameAndPassword(String username, String password) {
        String sql = """
        SELECT * FROM user WHERE username = ? AND password = MD5(?)
        """;
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return template.queryForObject(sql, rowMapper, username, password);
    }

    public void addUser(User user) {
        String sql = """
        INSERT INTO user (user_id, fname, lname, username, email, phone, password, role)
        VALUES (?, ?, ?, ?, ?, ?, MD5(?), ?)
    """;

        template.update(sql,
                getNextUserID(),
                user.getFname(),
                user.getLname(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword(),
                user.getRole()
        );
    }

    public User findUser(String fname, String lname) {
        String sql = """
        SELECT * FROM user WHERE fname LIKE ? AND lname LIKE ?
        """;
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return template.queryForObject(sql, rowMapper, fname, lname);
    }

    public void deleteUserByUsername(String username) {
        String sql = """
        DELETE FROM user WHERE username = ?
        """;
        template.update(sql, username);
    }

    public void updateUser(User user) {
        String sql = """
        UPDATE user
        SET fname = ?, lname = ?, email = ?, phone = ?, role = ?
        WHERE username = ?
    """;

        template.update(sql,
                user.getFname(),
                user.getLname(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getUsername()
        );
    }

    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE user SET password = MD5(?) WHERE username = ?";
        template.update(sql, newPassword, username);
    }

    public int getNextUserID() {
        String sql = "SELECT MAX(user_id) FROM user";
        Integer maxId = template.queryForObject(sql, Integer.class);
        return (maxId != null) ? maxId + 1 : 1;
    }
}