package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.repository.DamageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageService {

    @Autowired
    private DamageRepository damageRepository;

    public void createDamage(Damage damage) {
        damageRepository.createDamage(damage);
    }

    public void deleteDamage(Damage damage) {
        damageRepository.deleteDamage(damage);
    }

    public void updateDamage(Damage damage) {
        damageRepository.updateDamage(damage);
    }

    public List<Damage> getAllDamages() {
        return damageRepository.fetchAllDamages();
    }

    public List<Damage> getDamagesByIds(List<Integer> ids) {
        return damageRepository.getDamagesByIds(ids);
    }

}
