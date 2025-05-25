package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.contracts.AdvanceAgreement;
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

    public int getNextAdvanceID() {
        String sql = "SELECT MAX(advance_id) FROM advance_agreement";
        Integer maxId = template.queryForObject(sql, Integer.class);
        return (maxId != null) ? maxId + 1 : 1;
    }

}
