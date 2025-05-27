package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.service.ConditionReportService;
import org.example.bilabonnement.service.PdfGeneratorService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.example.bilabonnement.service.DamageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
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
    private PdfGeneratorService pdfGeneratorService;

    /** Viser siden for oprettelse af tilstandsrapport*/
    @GetMapping("/condition-report")
    public String showForm(Model model) {
        List<RentalContract> completedContracts = contractRepo.fetchCompletedContractsId();
        List<Damage> allDamages = damageService.findAll();

        model.addAttribute("contracts", completedContracts);
        model.addAttribute("damages", allDamages);

        return "damage-registration/condition-report";
    }

    /** Viser landing page for tilstandrapport*/
    @GetMapping("/damage-dashboard")
    public String showDamageDashboard(Model model) {
        List<Damage> allDamages = damageService.findAll();
        model.addAttribute("damageList", allDamages);
        return "damage-registration/damage-dashboard";
    }

    /** Viser skadesoversigt siden */
    @GetMapping("/damageCRUD")
    public String showDamageCRUD(Model model) {
        List<Damage> allDamages = damageService.findAll();
        model.addAttribute("damageList", allDamages);
        return "damage-registration/damageCRUD";
    }

    /** Opretter en skade */
    @PostMapping("/damage/create")
    public String createDamage(@ModelAttribute Damage damage) {
        if ((damage.getDescription() != null && !damage.getDescription().isEmpty()) ||
                damage.getPrice() > 0) {
            damageService.createDamage(damage);
        }
        return "redirect:/condition-report";
    }

    /** Viser skade redigerings siden*/
    @GetMapping("/damage/edit")
    public String showEditDamageForm(@RequestParam("id") int id, Model model) {
        Damage damage = damageService.findById(id);
        model.addAttribute("damage", damage);
        return "damage-registration/edit-damage";
    }
    /** Redigerer en skade */
    @PostMapping("/damage/update")
    public String updateDamage(@ModelAttribute Damage damage) {
        damageService.updateDamage(damage);
        return "redirect:/damageCRUD";
    }

    /** Fjerne skade fra databasen via id*/
    @PostMapping("/damage/delete/{id}")
    public String deleteDamage(@PathVariable("id") int id) {
        damageService.deleteDamageById(id);
        return "redirect:/damageCRUD";
    }

    /** Tilføjer en tilstandsrapport til databasen*/
    @PostMapping("/condition-report/create")
    public String createConditionReport(
            @RequestParam int contractId,
            @RequestParam int odometer,
            @RequestParam(required = false) List<Integer> selectedDamages,
            RedirectAttributes redirectAttributes
    ) {
        RentalContract contract = contractRepo.findById(contractId);
        LocalDate returnDate = contract.getToDate();

        double totalCost = 0;
        if (selectedDamages != null && !selectedDamages.isEmpty()) {
            List<Damage> damages = damageService.getDamagesByIds(selectedDamages);
            totalCost = damages.stream().mapToDouble(Damage::getPrice).sum();
        }

        ConditionReport report = new ConditionReport();
        report.setReturn_date(returnDate);
        report.setReport_date(LocalDate.now());
        report.setCost(totalCost);
        report.setOdometer(odometer);
        report.setContract_id(contractId);

        conditionReportService.createReport(report);
        int reportId = conditionReportService.getLastInsertedId();

        if (selectedDamages != null) {
            for (int i = 0; i < selectedDamages.size(); i++) {
                int damageId = selectedDamages.get(i);
                conditionReportService.linkDamageToReport(reportId, damageId);
            }
        }

        redirectAttributes.addFlashAttribute("confirmation", true);
        redirectAttributes.addFlashAttribute("newReportId", reportId);

        return "redirect:/condition-report";
    }

    /** Mathias?*/
    @GetMapping("/condition-report/{id}")
    public String showConditionReport(@PathVariable int id, Model model) {
        ConditionReport report = conditionReportService.getReportById(id);
        model.addAttribute("report", report);
        return "damage-registration/condition-report";
    }

    /** Generering en tilstandsrapport pdf*/
    @GetMapping("/condition-report/pdf/{id}")
    public ResponseEntity<InputStreamResource> downloadConditionReportPdf(@PathVariable int id) {
        ConditionReport report = conditionReportService.getReportById(id);
        List<Damage> damages = conditionReportService.findDamagesByReportId(id);
        ByteArrayInputStream pdf = pdfGeneratorService.generateConditionReportPdf(report, damages);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=tilstandsrapport_" + id + ".pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));

    }

}
