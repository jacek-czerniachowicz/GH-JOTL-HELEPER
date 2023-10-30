package com.gloomhaven.helper.controller;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeLoginController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String showMyLoginPage() {

        return "login_form";
    }
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}

