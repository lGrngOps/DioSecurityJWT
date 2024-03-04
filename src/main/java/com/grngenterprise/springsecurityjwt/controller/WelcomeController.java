package com.grngenterprise.springsecurityjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping
    public String welcome(){
        return "Welcome to essa bagaça";
    }

    @GetMapping("/users")
    public String users(){
        return "Cliente Liberado";
    }

    @GetMapping("/managers")
    public String managers(){
        return "Admin Liberado";
    }
}
