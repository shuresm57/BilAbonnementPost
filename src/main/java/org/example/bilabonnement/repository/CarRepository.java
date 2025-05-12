package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarRepository {
    @Autowired
    JdbcTemplate template;

    public List<Car> fetchAllCars() {
        String sql = "select * from car";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    //henter data fra tabel Car ud fra status i SQL-database, og indsætter hver row som et element i en liste.
    public List<Car> fetchCarsByStatus(String status) {
        String sql = "SELECT * FROM car WHERE rental_status = ?";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, status);
    }
}
