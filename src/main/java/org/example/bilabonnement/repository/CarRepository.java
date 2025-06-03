package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CarRepository {
    @Autowired
    JdbcTemplate template;

    public List<Car> fetchAllCars() {
        String sql = """
                     SELECT c.car_id,
                            c.reg_no,
                            c.vin,
                            c.location,
                            c.rental_status,
                            c.img_url,
                            c.price,
                            cm.brand, 
                            cm.model,
                            c.odometer,
                            c.down_payment,
                            c.monthly_fee
                     FROM car c
                     JOIN car_model cm ON c.model_id = cm.model_id;""";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }


    public Map<Integer, String> fetchAllModelsAndBrandsByModel() {
        String sql = "SELECT model_id, model, brand FROM car_model";

        return template.query(sql, rs -> {
            Map<Integer, String> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getInt("model_id"), rs.getString("brand") + " " + rs.getString("model"));
            }
            return result;
        });
    }


    //henter data fra tabel Car ud fra status i SQL-database, og indsætter hver row som et element i en liste.
    public List<Car> fetchCarsByStatus(String status) {
        String sql = """
                     SELECT c.car_id, c.reg_no, c.vin, c.location, c.rental_status, c.img_url, c.price, cm.brand, cm.model, c.odometer, c.down_payment,c.monthly_fee
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
        String sql = "INSERT INTO car (car_id, reg_no, vin, location, rental_status, img_url, model_id, price, odometer, down_payment, monthly_fee ) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        template.update(sql, car_id, car.getRegNo(), car.getVin(), car.getLocation(), car.getRentalStatus(), car.getImgUrl(), car.getModelId(), car.getPrice(), car.getOdometer(), car.getDownPayment(), car.getMonthlyFee());
    }

    public void addModel(String model, String brand) {
        int model_id = getNextModelID();
        String sql = "INSERT INTO car_model (model_id, brand, model) VALUES (?,?,?);";
        template.update(sql, model_id, brand, model);
    }

    public boolean deleteCar(int carId){
        String sql = "DELETE FROM car where car_id=?";
        return template.update(sql,carId)>0;
    }

    public Map<String, Integer> getBrandRentalFrequency(){
        String sql = """
                SELECT cm.brand, COUNT(*) as freq
                FROM car_model cm
                JOIN car c ON c.model_id = cm.model_id
                JOIN rental_contract rc ON rc.car_id = c.car_id
                GROUP BY cm.brand;
                """;
        return template.query(sql, rs -> {
            Map<String, Integer> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getString("brand"), rs.getInt("freq"));
            }
            return result;
        });
    }

}
