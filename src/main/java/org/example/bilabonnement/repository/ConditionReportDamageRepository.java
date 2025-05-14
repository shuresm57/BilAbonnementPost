package org.example.bilabonnement.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionReportDamageRepository {

    @Autowired
    private JdbcTemplate template;

    public void linkDamageToReport(int reportId, int damageId) {
        String sql = "INSERT INTO condition_report_damage (report_id, damage_id) VALUES (?, ?)";
        template.update(sql, reportId, damageId);
    }
}
