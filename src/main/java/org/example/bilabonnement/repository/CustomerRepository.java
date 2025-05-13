package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> fetchAll(){
        String sql = """
    SELECT 
        customer_id,
        fname AS first_name,
        lname AS last_name,
        phone_no,
        license_no,
        street_name,
        zip,
        email
    FROM customer
""";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
    }
    public void addCustomer(Customer customer) {
        // First: Check if the zip exists in the city table
        String checkSql = "SELECT COUNT(*) FROM city WHERE zip = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, customer.getZip());

        // If it doesn't exist → insert it
        if (count == null || count == 0) {
            String insertCitySql = "INSERT INTO city (zip, city_name) VALUES (?, ?)";
            jdbcTemplate.update(insertCitySql, customer.getZip(), customer.getCityName());
        }

        String sql = "INSERT INTO customer (customer_id, fname, lname, email, phone_no, license_no, street_name, zip) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getLicenseNo(),
                customer.getStreetName(),
                customer.getZip());
    }

    public int getNextCustomerId() {
        String sql = "SELECT COALESCE(MAX(customer_id), 0) + 1 FROM customer";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
