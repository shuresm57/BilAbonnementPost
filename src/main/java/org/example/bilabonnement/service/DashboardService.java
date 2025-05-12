package org.example.bilabonnement.service;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    DashboardRepository repo;

    public List<Car> fetchAllCars(){
        return repo.fetchAllCars();
    }

    public List<Car> fetchAvailableCars(){
        return repo.fetchCarsByStatus("AVAILABLE");
    }

    public List<Car> fetchDamagedCars() {
        return repo.fetchCarsByStatus("DAMAGED");
    }

    public List<Car> fetchRentedCars() {
        return repo.fetchCarsByStatus("RENTED");
    }
}
