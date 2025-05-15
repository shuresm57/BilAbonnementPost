package org.example.bilabonnement.controller;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.Customer;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.service.CarService;
import org.example.bilabonnement.service.DashboardService;
import org.example.bilabonnement.service.RentalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    @Autowired
    DashboardService service;
    @Autowired
    CarService carService;


    @GetMapping("/car-dashboard")
    public String carDashboard(Model model) {
        List<Car> carList = service.fetchAllCars();
        model.addAttribute("carList", carList);
        return "car-dashboard";
    }

    @GetMapping("/addcar")
    public String addCar() {
        return "redirect:/car-dashboard/addcar";
    }

    @GetMapping("/car-dashboard/addcar")
    public String showAddCarForm(Model model) {
        model.addAttribute("modelList", carService.fetchBrandAndModel());
        List<Car> carList = service.fetchAllCars(); // hvis du stadig bruger det
        model.addAttribute("carList", carList);
        return "addcar";
    }

    @GetMapping("/addmodel")
    public String addModel() {
        return "redirect:/car-dashboard/addmodel";
    }

    @PostMapping("/car-dashboard/addmodel")
    public String addModel(@RequestParam String brand, @RequestParam("model") String modelName) {
        carService.addModel(brand, modelName);
        return "redirect:/car-dashboard/addmodel";
    }


    @GetMapping("/car-dashboard/addmodel")
    public String showAddModelForm(Model model) {
        model.addAttribute("modelList", carService.fetchBrandAndModel());
        return "addmodel";
    }


    @PostMapping("/car-dashboard/addcar")
    public String addCar(Model model, Car car) {
        carService.addCar(car);
        return "redirect:/car-dashboard";
    }


    @GetMapping("/dashboard-selector")
    public String selector(Model model) {
        List<RentalContract> rentalContractList = service.fetchAllRentalContracts();
        model.addAttribute("rentalContracts", rentalContractList);

        return "dashboard-selector";
    }


    @GetMapping("/car-dashboard/{status}")
    public String viewCarsByStatus(@PathVariable String status, Model model) {
        List<Car> cars;
        switch (status.toLowerCase()) {
            case "damaged":
                cars = service.fetchDamagedCars();
                break;
            case "available":
                cars = service.fetchAvailableCars();
                break;
            case "rented":
                cars = service.fetchRentedCars();
                break;
            default:
                cars = new ArrayList<>();
        }
        model.addAttribute("carList", cars);
        return "car-dashboard";
    }

}
