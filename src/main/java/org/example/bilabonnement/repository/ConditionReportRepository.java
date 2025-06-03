package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ConditionReportRepository {
    @Autowired
    private JdbcTemplate template;

    /** Opretter en tilstandsrapport med contract_id fra rental contract */
    public void createConditionReport(ConditionReport report) {
        String sql = "INSERT INTO condition_report (return_date, report_date, cost, odometer, contract_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        template.update(sql,
                report.getReturn_date(),
                report.getReport_date(),
                report.getCost(),
                report.getOdometer(),
                report.getContract_id()
        );
    }

    /** Fetcher tilstandsrapport via id */
    public ConditionReport findConditionReportById(int id) {
        String sql = """
    SELECT
        cr.condition_report_id,
        cr.return_date,
        cr.report_date,
        cr.cost,
        cr.odometer,
        cr.contract_id,

        rc.customer_id,
        CONCAT(c.fname, ' ', c.lname) AS customerName,
        c.email,

        rc.car_id,
        car.reg_no,
        cm.brand,
        cm.model
    FROM condition_report cr
    JOIN rental_contract rc ON cr.contract_id = rc.contract_id
    JOIN customer c ON rc.customer_id = c.customer_id
    JOIN car ON rc.car_id = car.car_id
    JOIN car_model cm ON car.model_id = cm.model_id
    WHERE cr.condition_report_id = ?
""";
        RowMapper<ConditionReport> rowMapper = new BeanPropertyRowMapper<>(ConditionReport.class);
        return template.queryForObject(sql, rowMapper, id);
    }

    /** Fetcher skader via reporId (Bruges til pdf generering) */
    public List<Damage> findDamagesByReportId(int reportId) {
        String sql = """
        SELECT d.damage_id,
               d.description,
               d.price
          FROM condition_report_damage crd
          JOIN damage d
            ON crd.damage_id = d.damage_id
         WHERE crd.report_id = ?;
        """;

        RowMapper<Damage> rowMapper = new BeanPropertyRowMapper<>(Damage.class);
        return template.query(sql, rowMapper, reportId);
    }


    /** Fetcher alle skadet biler, der har været skadet i over 5 dage */
    public List<Car> findDamagedCarsOlderThanFiveDays() {
        String sql = """
            SELECT *
            FROM car c
            JOIN car_model cm ON c.model_id = cm.model_id
            JOIN rental_contract rc ON c.car_id = rc.car_id
            JOIN condition_report cr ON rc.contract_id = cr.contract_id
            WHERE c.rental_status = 'DAMAGED'
              AND cr.report_date <= CURRENT_DATE - INTERVAL 5 DAY;
        """;

        return template.query(sql, new BeanPropertyRowMapper<>(Car.class));
    }


    /** Bruges i sammenhæng med createConditionReport for at hente ID’et på den rapport,
     * der netop er oprettet, så skader kan linkes korrekt til den via en relationstabel.*/
    public int getLastInsertedId() {
        return template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    /**  Opretter en relation mellem en skadesrapport og en skade i databasen.
     * Denne metode indsætter en række i relationstabellen `condition_report_damage`,
     * hvilket gør det muligt at knytte en eller flere skader til en specifik tilstandsrapport. */
    public void  linkDamageToReport(int reportId, int damageId) {
        String sql = "INSERT INTO condition_report_damage (report_id, damage_id) VALUES (?, ?)";
        template.update(sql, reportId, damageId);
    }
}
