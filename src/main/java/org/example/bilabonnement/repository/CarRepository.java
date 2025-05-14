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

    public int getNextCarID(){
        String sql = "select max(car_id) from car";
        int maxId = template.queryForObject(sql, Integer.class);
        return maxId + 1;
    }

    public int getNextModelID(){
        String sql = "select max(model_id) from car_model";
        int maxId = template.queryForObject(sql, Integer.class);
        return maxId + 1;
    }

    public void addCar(Car car) {
        int car_id = getNextCarID();
        String sql = "INSERT INTO car (car_id, reg_no, vin, location, rental_status, img_url, model_id, price) VALUES (?,?,?,?,?,?,?,?);";
        template.update(sql, car_id, car.getRegNo(), car.getVin(), car.getLocation(), car.getRentalStatus(), car.getImgUrl(), car.getModelId(), car.getPrice());

    }

}
