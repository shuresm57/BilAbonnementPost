package org.example.bilabonnement.repository;

import org.example.bilabonnement.model.Damage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DamageCRUDRepo extends CrudRepository<Damage, Integer> {

    /**
     hvis der er tvivl om metoder
     kan de findes her:
     https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
     */

}
