package org.example.bilabonnement.repository;


import org.example.bilabonnement.model.contracts.EndContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EndContractRepository {

    @Autowired
    JdbcTemplate template;

    public List<EndContract> findAll() {
        String sql = "SELECT * FROM end_report";
        RowMapper<EndContract> rowMapper = new BeanPropertyRowMapper<>(EndContract.class);
        return template.query(sql, rowMapper);
    }

}
