package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RentalContractRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<RentalContract> fetchAllRentalContracts(){
        String sql = "SELECT * FROM rental_contract;";
        RowMapper<RentalContract> rowmapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(sql, rowmapper);
    }

    public List<RentalContract> fetchCompletedContracts(){
        String sql = "select * from rental_contract where to_date < current_date();";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<RentalContract> fetchOngoingContracts(){
        String sql = "Select * from rental_contract where to_date > current_date();";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    //Opretter en ny lejeaftale
    public void create(RentalContract contract) {
        String sql = "INSERT INTO rental_contract (contract_id, from_date, to_date, price, max_km, user_id, car_id, customer_id, advance_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                contract.getContractId(),
                contract.getFromDate(),
                contract.getToDate(),
                contract.getPrice(),
                contract.getMaxKm(),
                contract.getUserId(),
                contract.getCarId(),
                contract.getCustomerId(),
                contract.getAdvanceId());
    }
}
