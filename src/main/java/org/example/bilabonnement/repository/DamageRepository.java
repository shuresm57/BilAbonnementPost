/*package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.Damage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DamageRepository {

    @Autowired
    private JdbcTemplate template;

    public void createDamage(Damage damage) {
        String sql = "INSERT INTO damage (description, price) VALUES (?, ?, ?)";
        template.update(sql, damage.getDescription(), damage.getPrice());
    }

    public void deleteDamage(Damage damage) {
        String sql = "DELETE FROM damage WHERE damage_id = ?";
        template.update(sql, damage.getDamage_id());
    }

    public void updateDamage(Damage damage) {
        String sql = "UPDATE damage SET description = ?, price = ? = ? WHERE damage_id = ?";
        template.update(sql, damage.getDescription(), damage.getPrice(), damage.getDamage_id()
        );
    }

    public List<Damage> fetchAllDamages() {
        String sql = "SELECT * FROM damage";
        return template.query(sql, (rs, rowNum) -> {
            Damage damage = new Damage();
            damage.setDamage_id(rs.getInt("damage_id"));
            damage.setDescription(rs.getString("description"));
            damage.setPrice(rs.getDouble("price"));
            return damage;
        });
    }

    public List<Damage> getDamagesByIds(List<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT * FROM damage WHERE damage_id IN (" + inSql + ")";
        return template.query(sql, ids.toArray(), (rs, rowNum) -> {
            Damage damage = new Damage();
            damage.setDamage_id(rs.getInt("damage_id"));
            damage.setDescription(rs.getString("description"));
            damage.setPrice(rs.getDouble("price"));
            return damage;
        });

    }
}
*/