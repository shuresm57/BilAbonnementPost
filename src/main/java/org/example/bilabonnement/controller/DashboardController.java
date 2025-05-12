package org.example.bilabonnement.controller;


import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DashboardController {
DashboardService service;

/*
@GetMapping
public String Dashboard(Model model) {
    List<Car> carList = service.fetchAllCars();

}
*/


@GetMapping("/dashboard")
    public String dashboardAvailable(Model model) {
    List<Car> carList = service.fetchAvailableCars();
    model.addAllAttributes(carList);
    return "dashboard";
}
/*
public String dashboardDamaged(Model model) {

}
*/



}
