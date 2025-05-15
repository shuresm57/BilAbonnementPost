package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.repository.DamageCRUDRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageService {
    @Autowired
    private DamageCRUDRepo damageCRUDRepo;

    public List<Damage> findAll()
    {
        return (List<Damage>) damageCRUDRepo.findAll();
    }

    public void createDamage(Damage damage) {damageCRUDRepo.save(damage);}

    public void deleteDamage(Damage damage) {
        damageCRUDRepo.delete(damage);
    }

    public List<Damage> getDamagesByIds(List<Integer> ids) {
        return (List<Damage>) damageCRUDRepo.findAllById(ids);
    }
}
