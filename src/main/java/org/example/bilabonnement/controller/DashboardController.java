package org.example.bilabonnement.controller;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
DashboardService service;

@GetMapping("/dashboard")
public String Dashboard(Model model) {
    List<Car> carList = service.fetchAllCars();
    model.addAttribute("carList", carList);
    return "dashboard";
}

    @GetMapping("/dashboard/{status}")
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
        return "dashboard";
    }

}
