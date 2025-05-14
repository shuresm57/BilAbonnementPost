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
        String sql = """
                     SELECT c.reg_no, c.vin, c.location, c.rental_status, c.img_url, c.price, cm.brand, cm.model
                     FROM car c
                     JOIN car_model cm ON c.model_id = cm.model_id;""";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    //henter data fra tabel Car ud fra status i SQL-database, og indsætter hver row som et element i en liste.
    public List<Car> fetchCarsByStatus(String status) {
        String sql = """
             SELECT c.reg_no, c.vin, c.location, c.rental_status, c.img_url, c.price, cm.brand, cm.model
             FROM car c
             JOIN car_model cm ON c.model_id = cm.model_id
             WHERE c.rental_status = ?""";

        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, status);
    }
}
