package com.tracw.tracw.controller;

import com.tracw.tracw.dto.UserDto;
import com.tracw.tracw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    // Display the registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register"; // returns src/main/resources/templates/register.html
    }

    // Process registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto, Model model) {
        try {
            userService.registerUser(
                    userDto.getUsername(),
                    userDto.getPassword(),
                    userDto.getPhoneNumber(),
                    userDto.getBalance());
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
