package org.example.bilabonnement.controller;
import org.example.bilabonnement.model.Customer;
import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.contracts.AdvanceAgreement;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RentalContractController {

    @Autowired
    private RentalContractService rentalContractService;
    @Autowired
    private CarService carService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PdfGeneratorService pdfGeneratorService;
    @Autowired
    private AdvanceAgreementService advanceAgreementService;

    @GetMapping("/rental-contract/new")
    public String showForm(Model model, @ModelAttribute("customer") Customer customer) {
        model.addAttribute("contract", new RentalContract());
        model.addAttribute("cars", carService.fetchAllCars());
        model.addAttribute("users", userService.fetchAllUsers());
        model.addAttribute("customers", customerService.fetchAll());
        if (customer == null || customer.getCustomerId() == 0) {
            Customer newCustomer = new Customer();
            newCustomer.setCustomerId(customerService.getNextCustomerId());
            model.addAttribute("customer", newCustomer);
        } else {
            model.addAttribute("customer", customer);
        }
        return "rental-contract-form";
    }

    @PostMapping("/rental-contract/save")
    public String saveContract(@ModelAttribute RentalContract contract, RedirectAttributes redirectAttributes) {
        rentalContractService.createRentalContract(contract);

        redirectAttributes.addFlashAttribute("confirmation", true);
        redirectAttributes.addFlashAttribute("newContractId", contract.getContractId());

        return "redirect:/rental-contract-new";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomer(customer);
        // Redirect back to the rental contract form
        return "redirect:/rental-contract/new";
    }

    @GetMapping("/rental-contract")
    public String rentalContract(Model model){
     List<RentalContract> rentalContractList = rentalContractService.fetchAllRentalContracts();
        model.addAttribute("rentalContracts", rentalContractList);
        return "rental-contract";
    }

    @GetMapping("/rental-contract/{status}")
    public String viewRentalContractsByStatus(@PathVariable String status, Model model){
        List<RentalContract> rentalContracts;
        String statusTitle = "";
        int totalPrice;

        switch(status){
            case "ongoing":
                rentalContracts = rentalContractService.fetchOngoingRentalContracts();
                statusTitle = "I alt (igangværende aftaler)";
                break;
            case "completed":
                rentalContracts = rentalContractService.fetchCompletedRentalContracts();
                statusTitle = "I alt (afsluttede aftaler)";
                break;
            case "all":
                rentalContracts = rentalContractService.fetchAllRentalContracts();
                statusTitle = "I alt (alle aftaler)";
                break;
            default:
                rentalContracts = new ArrayList<>();
        }

        totalPrice = rentalContracts.stream()
                .mapToInt(RentalContract::getPrice)
                .sum();
        model.addAttribute("rentalContracts", rentalContracts);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("statusTitle", statusTitle);
        return "rental-contract";
    }

    @GetMapping("/rental-contract/pdf/{id}")
    public ResponseEntity<InputStreamResource> generatePdf(@PathVariable int id) {
        RentalContract contract = rentalContractService.findById(id); // Your existing method
        ByteArrayInputStream pdf = pdfGeneratorService.generateRentalContractPdf(contract);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=lejeaftale_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    @GetMapping("/advance-agreement/{id}")
    public String showAdvanceAgreement(@PathVariable int id, Model model) {
        AdvanceAgreement agreement = advanceAgreementService.getAdvanceAgreementById(id);
        model.addAttribute("agreement", agreement);
        return "advance-agreement-details";
    }

}
