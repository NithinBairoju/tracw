package com.tracw.tracw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // GET /login will serve our custom login page
    @GetMapping("/login")
    public String login() {
        return "login"; // returns src/main/resources/templates/login.html
    }
}
