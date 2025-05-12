package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentalContractRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
