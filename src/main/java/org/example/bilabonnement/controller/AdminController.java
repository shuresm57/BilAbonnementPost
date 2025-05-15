package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.contracts.AdvanceAgreement;
import org.example.bilabonnement.model.user.User;
import org.example.bilabonnement.service.AdvanceAgreementService;
import org.example.bilabonnement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", userService.fetchAllUsers());
        return "admin";
    }

    @GetMapping("/admin/user/{username}")
    public String showUserCrud(@PathVariable String username, Model model) {
        User user = userService.findByUsernameInMap(username);
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

    @GetMapping("/admin/create-user")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/admin/create-user")
    public String handleCreateUser(@ModelAttribute User user) {
        user.setUsername(userService.generateUsername(user.getFname(), user.getLname()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete-user")
    public String deleteUser(@RequestParam String username) {
        userService.deleteUserByUsername(username);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete-user-confirm")
    public String showDeleteConfirmation(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "confirm-delete";
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

    @PostMapping("/admin/update-user")
    public String updateUser(@RequestParam String username,
                             @RequestParam String fname,
                             @RequestParam String lname,
                             @RequestParam String email,
                             @RequestParam String phoneNo,
                             @RequestParam String role) {

        User user = userService.findByUsernameInMap(username);
        user.setFname(fname);
        user.setLname(lname);
        user.setEmail(email);
        user.setPhone(phoneNo);
        user.setRole(role);

        userService.updateUser(user);
        userService.clearUserCache();

        return "redirect:/admin/user/" + username;
    }


}
