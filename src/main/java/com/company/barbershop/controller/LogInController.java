package com.company.barbershop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController {

    @GetMapping("/mostrarLogIn")
    public String mostrarLogIn(){

        return "log-in";
    }
}
