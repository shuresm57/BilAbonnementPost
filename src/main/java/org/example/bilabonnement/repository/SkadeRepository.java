package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.Skade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkadeRepository {

    @Autowired
    private JdbcTemplate template;


    public void createSkade(Skade skade) {
        String sql = "INSERT INTO skade (description, price, img_url, report_id) VALUES (?, ?, ?, ?)";
        template.update(sql, skade.getDescription(), skade.getPrice(), skade.getImg_url(), skade.getReport_id());
    }

    public void deleteSkade(Skade skade) {
        String sql = "DELETE FROM skade WHERE damage_id = ?";
        template.update(sql, skade.getDamage_id());
    }

    public void updateSkade(Skade skade) {
        String sql = "UPDATE skade SET description = ?, price = ?, img_url = ?, report_id = ? WHERE damage_id = ?";
        template.update(sql, skade.getDescription(), skade.getPrice(), skade.getImg_url(), skade.getReport_id(), skade.getDamage_id()
        );
    }

    public List<Skade> fetchAllSkader() {
        String sql = "SELECT * FROM skade";
        return template.query(sql, (rs, rowNum) -> {
            Skade skade = new Skade();
            skade.setDamage_id(rs.getInt("damage_id"));
            skade.setDescription(rs.getString("description"));
            skade.setPrice(rs.getDouble("price"));
            skade.setImg_url(rs.getString("img_url"));
            skade.setReport_id(rs.getInt("report_id"));
            return skade;
        });
    }
}
