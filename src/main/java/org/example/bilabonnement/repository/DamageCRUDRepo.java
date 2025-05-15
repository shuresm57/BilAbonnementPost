package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.Damage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageCRUDRepo extends CrudRepository<Damage, Integer> {

}
