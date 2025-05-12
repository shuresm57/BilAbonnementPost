package org.example.bilabonnement.controller;

import org.example.bilabonnement.model.user.User;
import org.example.bilabonnement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-list")
    public String showAllUsers(Model model) {
        List<User> userList = userService.fetchAllUsers();
        model.addAttribute("userList", userList);
        return "user-list";
    }
}
