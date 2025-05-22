package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.user.User;
import org.example.bilabonnement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        User user = userService.findByUsernameAndPassword(username, password);

        if (user != null) {
            model.addAttribute("user", user);

            return switch (user.getRole()) {
                case "DATA" -> "redirect:/rental-contract";
                case "SKADE" -> "redirect:/damage-dashboard";
                case "UDVIKLING" -> "redirect:/business-dashboard";
                case "ADMIN" -> "redirect:/admin";
                default -> {
                    model.addAttribute("error", "Ukendt rolle.");
                    yield "login";
                }
            };
        } else {
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "admin/login";
        }
    }

}
