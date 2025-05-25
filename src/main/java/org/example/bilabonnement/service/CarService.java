package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.repository.CarRepository;
import org.example.bilabonnement.repository.ConditionReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    CarRepository repo;


    public void addModel(String model, String brand){
        repo.addModel(model, brand);
    }

    public List<Car> fetchAllCars(){
        return repo.fetchAllCars();
    }

    public void addCar(Car car){
        repo.addCar(car);
    }

    public boolean deleteCar(int carId){
        return repo.deleteCar(carId);
    }

    public Map<Integer,String> fetchAllModelsAndBrandsByModel(){
        return repo.fetchAllModelsAndBrandsByModel();
    }

}
