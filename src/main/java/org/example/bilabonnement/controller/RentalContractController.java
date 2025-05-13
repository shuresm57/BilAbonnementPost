package org.example.bilabonnement.controller;

<<<<<<< Updated upstream
import org.example.bilabonnement.model.Customer;
=======
import org.example.bilabonnement.model.Car;
>>>>>>> Stashed changes
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.service.CarService;
import org.example.bilabonnement.service.CustomerService;
import org.example.bilabonnement.service.RentalContractService;
import org.example.bilabonnement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/rental-contract/new")
    public String showForm(Model model, @ModelAttribute("customer") Customer customer) {
        model.addAttribute("contract", new RentalContract());
        model.addAttribute("cars", carService.fetchAllCars());
        //model.addAttribute("users", userService.fetchAllUsers());
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
    public String saveContract(@ModelAttribute RentalContract contract) {
        rentalContractService.createRentalContract(contract);
        return "redirect:/rental-contract-new";
    }

<<<<<<< Updated upstream
    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute Customer customer) {
        customerService.addCustomer(customer);
        // Redirect back to the rental contract form
        return "redirect:/rental-contract/new";
    }

=======
    @GetMapping("/rental-contract")
    public String rentalContract(Model model){
     List<RentalContract> rentalContractList = rentalContractService.fetchAllRentalContracts();
        model.addAttribute("rentalContracts", rentalContractList);
        return "rental-contract";
    }

    @GetMapping("/rental-contract/{status}")
    public String viewRentalContractsByStatus(@PathVariable String status, Model model){
        List<RentalContract> rentalContracts;
        switch(status){
            case "ongoing":
                rentalContracts = rentalContractService.fetchOngoingRentalContracts();
                break;
            case "completed":
                rentalContracts = rentalContractService.fetchCompletedRentalContracts();
                break;
            case "all":
                rentalContracts = rentalContractService.fetchAllRentalContracts();
                break;
            default:
                rentalContracts = new ArrayList<>();
        }
        model.addAttribute("rentalContracts", rentalContracts);
        return "rental-contract";
    }


>>>>>>> Stashed changes
}
