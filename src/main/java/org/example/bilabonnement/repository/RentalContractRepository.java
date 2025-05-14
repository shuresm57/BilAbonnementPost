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

//Henter alle biler (inklusiv customer-name, for at kunne displaye)
    public List<RentalContract> fetchAllRentalContracts(){
        String sql = """
    SELECT rc.contract_id AS contractId,
           rc.from_date,
           rc.to_date,
           rc.price,
           rc.max_km,
           CONCAT(c.fname, ' ', c.lname) AS customerName
    FROM rental_contract rc
    JOIN customer c ON rc.customer_id = c.customer_id
""";

        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

//Fetcher færdige kontrakter
    public List<RentalContract> fetchCompletedContracts() {
        String sql = """
    SELECT rc.contract_id AS contractId,
           rc.from_date,
           rc.to_date,
           rc.price,
           rc.max_km,
           CONCAT(c.fname, ' ', c.lname) AS customerName
    FROM rental_contract rc
    JOIN customer c ON rc.customer_id = c.customer_id
    WHERE rc.to_date < CURRENT_DATE()
""";

        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<RentalContract> fetchOngoingContracts() {
        String sql = """
    SELECT rc.contract_id AS contractId,
           rc.from_date,
           rc.to_date,
           rc.price,
           rc.max_km,
           CONCAT(c.fname, ' ', c.lname) AS customerName
    FROM rental_contract rc
    JOIN customer c ON rc.customer_id = c.customer_id
    WHERE rc.to_date >= CURRENT_DATE()
""";

        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    //Fetcher leje-kontrakter på ID (bruges i forbindelse med PDF)
    public RentalContract findById(int id) {
        String sql = """
        SELECT rc.contract_id AS contractId,
               rc.from_date,
               rc.to_date,
               rc.price,
               rc.max_km,
               CONCAT(c.fname, ' ', c.lname) AS customerName,
               CONCAT(cm.brand, ' ', cm.model, ' - ', car.reg_no) AS carDescription
        FROM rental_contract rc
        JOIN customer c ON rc.customer_id = c.customer_id
        JOIN car ON rc.car_id = car.car_id
        JOIN car_model cm ON car.model_id = cm.model_id
        WHERE rc.contract_id = ?
    """;

        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
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
