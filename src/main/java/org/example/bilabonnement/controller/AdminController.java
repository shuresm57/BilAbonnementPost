package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.user.User;
import org.example.bilabonnement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        Map<String, User> userMap = userService.userMap();
        List<User> userList = new ArrayList<>(userMap.values());
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
}
