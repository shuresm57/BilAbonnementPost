package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;


    public void addModel(String model, String brand){
        carRepository.addModel(model, brand);
    }

    public List<Car> fetchAllCars(){
        return carRepository.fetchAllCars();
    }

    public void addCar(Car car){
        carRepository.addCar(car);
    }

    public boolean deleteCar(int carId){
        return carRepository.deleteCar(carId);
    }

    public Map<String, String> fetchAllModelsAndBrandsByModel(){
        return carRepository.fetchAllModelsAndBrandsByModel();
    }

}
