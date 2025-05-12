package org.example.bilabonnement.service;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.repository.CarRepository;
import org.example.bilabonnement.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private CarRepository repo;

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
