package org.example.bilabonnement.service;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.CarRepository;
import org.example.bilabonnement.repository.ConditionReportRepository;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RentalContractRepository rentalContractRepo;
    @Autowired
    private ConditionReportRepository conditionReportRepo;

    public List<Car> fetchAllCars(){
        return carRepository.fetchAllCars();
    }

    public List<RentalContract> fetchAllRentalContracts(){
        return rentalContractRepo.fetchAllRentalContracts();
    }

    public List<Car> fetchAvailableCars(){
        return carRepository.fetchCarsByStatus("AVAILABLE");
    }

    public List<Car> fetchDamagedCars() {
        return carRepository.fetchCarsByStatus("DAMAGED");
    }

    public List<Car> fetchRentedCars() {
        return carRepository.fetchCarsByStatus("RENTED");
    }

    public List<Car> findDamagedCarsOlderThanFiveDays() {
        return conditionReportRepo.findDamagedCarsOlderThanFiveDays();
    }

}
