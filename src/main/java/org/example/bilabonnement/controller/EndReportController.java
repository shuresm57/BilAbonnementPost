package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.contracts.EndContract;
import org.example.bilabonnement.repository.EndContractRepository;
import org.example.bilabonnement.service.EndContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EndReportController {

    @Autowired
    private EndContractService endContractService;

    @GetMapping("/end-report")
    public String endReport(Model model) {
        List<EndContract> endReports = endContractService.fetchAllEndContracts();
        model.addAttribute("endReports", endReports);
        return "data-registration/end-report";
    }

}
