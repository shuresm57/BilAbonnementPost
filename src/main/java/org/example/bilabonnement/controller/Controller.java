package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.service.CarService;
import org.example.bilabonnement.service.PieService;
import org.example.bilabonnement.service.RentalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    PieService pieService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("slices", pieService.generateSlices());
        return "index";
    }



}
