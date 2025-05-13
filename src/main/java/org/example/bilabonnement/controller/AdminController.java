package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.user.User;
import org.example.bilabonnement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> userList = userService.fetchAllUsers();
        model.addAttribute("userList", userList);
        return "admin";
    }

    @GetMapping("/admin/user/{username}")
    public String showUserCrud(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "Bruger ikke fundet");
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        return "user-crud";
    }

    @GetMapping("/admin/change-password/{username}")
    public String showChangePasswordForm(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "change-password";
    }

    @PostMapping("/admin/change-password")
    public String processPasswordChange(@RequestParam String username,
                                        @RequestParam String oldPassword,
                                        @RequestParam String newPassword,
                                        @RequestParam String confirmPassword,
                                        Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "De nye adgangskoder matcher ikke.");
            model.addAttribute("username", username);
            return "change-password";
        }

        User user = userService.findByUsernameAndPassword(username, oldPassword);
        if (user == null) {
            model.addAttribute("error", "Forkert nuværende adgangskode.");
            model.addAttribute("username", username);
            return "change-password";
        }

        userService.updatePassword(username, newPassword);
        return "redirect:/admin/user/" + username;
    }
}
