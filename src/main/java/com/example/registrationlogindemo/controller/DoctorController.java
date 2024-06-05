package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.DoctorScheduleDto;
import com.example.registrationlogindemo.service.DoctorScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorScheduleService doctorScheduleService;

    public DoctorController(DoctorScheduleService doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    @GetMapping("/")
    public String doctorDashboard(Model model, Principal principal) {
        // Retrieve the currently logged-in doctor's schedule
        List<DoctorScheduleDto> doctorSchedule = doctorScheduleService.getDoctorScheduleForLoggedInDoctor(principal);

        // Add the doctor's schedule to the model
        model.addAttribute("doctorSchedule", doctorSchedule);

        return "doctor/doctorDashboard"; // Return the view associated with the doctor's dashboard
    }
}
