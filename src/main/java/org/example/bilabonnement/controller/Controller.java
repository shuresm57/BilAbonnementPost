package org.example.bilabonnement.controller;

import org.example.bilabonnement.service.PieSliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    PieSliceService pieSliceService;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("slices", pieSliceService.generateRentalSlices());
        return "admin/index";
    }

    /*@GetMapping("/charts/area-chart")
    public String paymentArea(Model model) {
        model.addAttribute("paymentAreaSlices", pieSliceService.generatePaymentAreaSlices());
        return "charts/area-chart";
    }*/
}
