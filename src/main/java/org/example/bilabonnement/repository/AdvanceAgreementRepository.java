package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.contracts.AdvanceAgreement;
import org.example.bilabonnement.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdvanceAgreementRepository {

    @Autowired
    JdbcTemplate template;


    public List<AdvanceAgreement> getAllAdvanceAgreements() {
        String sql = "SELECT * FROM advance_agreement";
        RowMapper<AdvanceAgreement> rowMapper = new BeanPropertyRowMapper<>(AdvanceAgreement.class);
        return template.query(sql, rowMapper);
    }

    public AdvanceAgreement getAdvanceAgreementById(int id) {
        String sql = "SELECT * FROM advance_agreement WHERE advance_id = ?";
        RowMapper<AdvanceAgreement> rowMapper = new BeanPropertyRowMapper<>(AdvanceAgreement.class);
        return template.queryForObject(sql, rowMapper, id);
    }

    public void saveAdvanceAgreement(AdvanceAgreement advanceAgreement) {
        String sql = "INSERT INTO advance_agreement (advance_id, currency, pickup_location, car_bought) VALUES (?, ?, ?, ?)";
        template.update(sql,
                getNextAdvanceID(),
                advanceAgreement.getCurrency(),
                advanceAgreement.getPickupLocation(),
                advanceAgreement.isCarBought()
        );
    }

    public void deleteAdvanceAgreementById(int id) {
        String sql = "DELETE FROM advance_agreement WHERE advance_id = ?";
        template.update(sql, id);
    }

    public void updateCurrency(int id, String currency) {
        String sql = "UPDATE advance_agreement SET currency = ? WHERE advance_id = ?";
        template.update(sql, currency, id);
    }

    public void updateLocation(int id, String location) {
        String sql = "UPDATE advance_agreement SET location = ? WHERE advance_id = ?";
        template.update(sql, location, id);
    }

    public void updateSale(int id, String sale) {
        String sql = "UPDATE advance_agreement SET car_bought = ? WHERE advance_id = ?";
        template.update(sql, sale, id);
    }

    public int getNextAdvanceID() {
        String sql = "SELECT MAX(advance_id) FROM advance_agreement";
        Integer maxId = template.queryForObject(sql, Integer.class);
        return (maxId != null) ? maxId + 1 : 1;
    }

}
