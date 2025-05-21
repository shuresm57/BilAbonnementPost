package org.example.bilabonnement.controller;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.service.CarService;
import org.example.bilabonnement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    DashboardService dashboardService;
    @Autowired
    CarService carService;


    @GetMapping("/car-dashboard")
    public String carDashboard(Model model) {
        List<Car> carList = dashboardService.fetchAllCars();
        model.addAttribute("carList", carList);
        List<Car> damagedCarsOverFive = dashboardService.findDamagedCarsOlderThanFiveDays();
        model.addAttribute("damagedCarsOverFive", damagedCarsOverFive);



        int totalPrice = carList.stream().mapToInt(car -> car.getPrice()).sum();

        model.addAttribute("carList", carList);
        model.addAttribute("totalPrice", totalPrice);


        return "car-dashboard";
    }

    @GetMapping("/addcar")
    public String addCar() {
        return "redirect:/car-dashboard/addcar";
    }

    @GetMapping("/car-dashboard/addcar")
    public String showAddCarForm(Model model) {
        model.addAttribute("modelList", carService.fetchBrandAndModel());
        List<Car> carList = dashboardService.fetchAllCars();
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
        List<RentalContract> rentalContractList = dashboardService.fetchAllRentalContracts();
        model.addAttribute("rentalContracts", rentalContractList);

        return "dashboard-selector";
    }

    @GetMapping("/car-dashboard/delete/{carId}")
    public String deleteCar(@PathVariable int carId,
                            RedirectAttributes redirectAttributes) {
        boolean deleted = carService.deleteCar(carId);
        redirectAttributes.addFlashAttribute("confirmation", deleted);
        return "redirect:/car-dashboard";
    }


    @GetMapping("/car-dashboard/{status}")
    public String viewCarsByStatus(@PathVariable String status, Model model) {
        List<Car> cars;
        List<Car> damagedCarsOverFive = null;
        switch (status.toLowerCase()) {
            case "damaged":
                cars = dashboardService.fetchDamagedCars();
                damagedCarsOverFive = dashboardService.findDamagedCarsOlderThanFiveDays();
                break;
            case "available":
                cars = dashboardService.fetchAvailableCars();
                break;
            case "rented":
                cars = dashboardService.fetchRentedCars();
                break;
            default:
                cars = new ArrayList<>();
        }

        int totalPrice = cars.stream()
                .mapToInt(Car::getPrice)
                .sum();

        model.addAttribute("carList", cars);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("damagedCarsOverFive", damagedCarsOverFive);

        // Sæt advarselsflag hvis det er "available" status og færre end 5 biler
        if ("available".equalsIgnoreCase(status)) {
            boolean lowAvailableWarning = cars.size() < 5;
            model.addAttribute("lowAvailableWarning", lowAvailableWarning);
        }

        return "car-dashboard";
    }



}
