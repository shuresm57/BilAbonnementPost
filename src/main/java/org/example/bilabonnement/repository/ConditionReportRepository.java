package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionReportRepository {
    @Autowired
    private JdbcTemplate template;

    public void createConditionReport(ConditionReport report) {
        String sql = "INSERT INTO condition_report (return_date, report_date, cost, km_travelled, contract_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        template.update(sql,
                report.getReturn_date(),
                report.getReport_date(),
                report.getCost(),
                report.getKm_travelled(),
                report.getContract_id()
        );
    }

    public ConditionReport findConditionReportById(int id) {
        String sql = """
            SELECT 
                condition_report_id,
                return_date,
                report_date,
                cost,
                km_travelled,
                contract_id
            FROM condition_report
            WHERE condition_report_id = ?
        """;

        RowMapper<ConditionReport> rowMapper = new BeanPropertyRowMapper<>(ConditionReport.class);
        return template.queryForObject(sql, rowMapper, id);
    }

    public int getLastInsertedId() {
        return template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }
}
