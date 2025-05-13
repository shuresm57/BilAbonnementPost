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
}
