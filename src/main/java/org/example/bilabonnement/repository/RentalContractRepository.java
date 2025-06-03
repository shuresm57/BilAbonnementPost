package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.contracts.AdvanceAgreement;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RentalContractRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public List<RentalContract> fetchAllRentalContracts(){
        String sql = """
                        SELECT rc.contract_id AS contractId,
                               rc.from_date,
                               rc.to_date,
                               rc.price,
                               rc.max_km,
                               rc.advance_id,
                               CONCAT(c.fname, ' ', c.lname) AS customerName
                        FROM rental_contract rc
                        JOIN customer c ON rc.customer_id = c.customer_id
                    """;

        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<RentalContract> fetchCompletedContracts() {
        String sql = """
                    SELECT rc.contract_id AS contractId,
                           rc.from_date,
                           rc.to_date,
                           rc.price,
                           rc.max_km,
                           rc.advance_id,
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
                               rc.advance_id,
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
                        SELECT 
                               rc.contract_id AS contractId,
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

    public List<RentalContract> fetchCompletedContractsId() {
        String sql = "SELECT rc.contract_id, rc.from_date, rc.to_date, rc.price, rc.max_km, CONCAT(c.fname, ' ', c.lname) AS customer_name " +
                "FROM rental_contract rc " +
                "JOIN customer c ON rc.customer_id = c.customer_id " +
                "WHERE rc.to_date < CURRENT_DATE()";
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

        String updateCarStatusSql = "UPDATE car SET rental_status = 'rented' WHERE car_id = ?";
        jdbcTemplate.update(updateCarStatusSql, contract.getCarId());
    }

    //Finder næste ledige ID
    public int getNextContractId()
    {
        String sql = "SELECT MAX(contract_id) FROM rental_contract";
        Integer maxId = jdbcTemplate.queryForObject(sql, Integer.class);
        return maxId != null ? maxId + 1 : 1;
    }

    //Sletter en leje-kontrakt
    public void deleteById(int contractId) {
        String sql = "DELETE FROM rental_contract WHERE contract_id = ?";
        setCarAvailableByContractId(contractId);
        jdbcTemplate.update(sql, contractId);
    }

    //Ændrer bilens availability
    public void setCarAvailableByContractId(int contractId) {
        String sql = "UPDATE car " +
                     "SET rental_status = 'available' " +
                     "WHERE car_id = (" +
                     "    SELECT car_id " +
                     "    FROM rental_contract " +
                     "    WHERE contract_id = ?" +
                     ")";
        jdbcTemplate.update(sql, contractId);
    }

    public Map<Integer,Integer> getMonthlyRevenue() {
        String sql = """
                SELECT
                    MONTH(rental_contract.to_date) AS month,
                    SUM(price) AS total_revenue
                FROM rental_contract
                GROUP BY MONTH(rental_contract.to_date);
                """;
        return jdbcTemplate.query(sql, rs -> {
            Map<Integer, Integer> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getInt("month"), rs.getInt("total_revenue"));
            }
            return result;
        });
    }
}
