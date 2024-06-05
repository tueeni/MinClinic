package com.example.registrationlogindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @GetMapping("/")
    public String home() {
        return "patient/main";
    }

    @GetMapping("/appointments")
    public String appointments(){
        return "patient/appointments";
    }
}
