package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.service.ConditionReportDamageService;
import org.example.bilabonnement.service.ConditionReportService;
import org.springframework.ui.Model;
import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.example.bilabonnement.repository.ConditionReportDamageRepository;
import org.example.bilabonnement.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ConditionReportController {

    @Autowired
    private DamageService damageService;

    @Autowired
    private RentalContractRepository contractRepo;

    @Autowired
    private ConditionReportService conditionReportService;

    @Autowired
    private ConditionReportDamageService conditionReportDamageService;

    @GetMapping("/condition-report")
    public String showForm(Model model) {
        List<RentalContract> completedContracts = contractRepo.fetchCompletedContractsId();
        List<Damage> allDamages = damageService.getAllDamages();

        model.addAttribute("contracts", completedContracts);
        model.addAttribute("damages", allDamages); // 🔥

        return "condition-report";
    }

    @PostMapping("/damage/create")
    public String createDamage(@ModelAttribute Damage damage) {
        // Her kan du evt. validere at mindst én af felterne er udfyldt, ellers spring oprettelse over
        if ((damage.getDescription() != null && !damage.getDescription().isEmpty()) ||
                damage.getPrice() > 0 || damage.getImg_url() != null) {
            damageService.createDamage(damage);
        }
        return "redirect:/condition-report";
    }

    @PostMapping("/condition-report/create")
    public String createConditionReport(
            @RequestParam int contractId,
            @RequestParam int kmTravelled,
            @RequestParam(required = false) List<Integer> selectedDamages
    ) {
        // 1. Hent kontraktens return_date og km
        RentalContract contract = contractRepo.fetchById(contractId);
        LocalDate returnDate = contract.getToDate();

        // 2. Beregn skadepris
        double totalCost = 0;
        if (selectedDamages != null && !selectedDamages.isEmpty()) {
            List<Damage> damages = damageService.getDamagesByIds(selectedDamages);
            totalCost = damages.stream().mapToDouble(Damage::getPrice).sum();
        }

        // 3. Opret rapportobjekt
        ConditionReport report = new ConditionReport();
        report.setReturn_date(returnDate);
        report.setReport_date(LocalDate.now());
        report.setCost(totalCost);
        report.setKm_travelled(kmTravelled);
        report.setContract_id(contractId);

        // 4. Gem rapporten
        conditionReportService.createReport(report);
        int reportId = conditionReportService.getLastInsertedId();

        // 5. Link skader til rapport
        if (selectedDamages != null) {
            for (Integer damageId : selectedDamages) {
                conditionReportDamageService.linkDamageToReport(reportId, damageId); // <-- Bruger den nye service
            }
        }

        return "redirect:/condition-report";
    }
}
