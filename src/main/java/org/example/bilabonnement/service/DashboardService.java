package org.example.bilabonnement.service;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.CarRepository;
import org.example.bilabonnement.repository.DashboardRepository;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private CarRepository repo;
    @Autowired
    private RentalContractRepository rentalContractRepo;

    public List<Car> fetchAllCars(){
        return repo.fetchAllCars();
    }

    public List<RentalContract> fetchAllRentalContracts(){
        return rentalContractRepo.fetchAllRentalContracts();
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
