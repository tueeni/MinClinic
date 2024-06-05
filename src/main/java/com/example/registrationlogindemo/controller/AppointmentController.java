package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.AppointmentDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.AppointmentService;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserService userService;

    public AppointmentController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    // Конструктор и другие методы контроллера
    @GetMapping("/new")
    public String showAppointmentForm(Model model) {
        // Populate model with necessary data for the appointment form
        AppointmentDto appointmentDto = new AppointmentDto();
        model.addAttribute("appointmentDto", appointmentDto);
        // Add more data to the model as needed
        List<User> doctors = userService.findDoctors(); // Получаем список врачей
        model.addAttribute("doctors", doctors);
        return "appointmentForm";
    }

    @PostMapping("/new")
    public String createAppointment(@ModelAttribute("appointmentDto") AppointmentDto appointmentDto, Principal principal) {
        // Get the username of the currently logged-in patient
        String patientName = principal.getName();
        // Delegate appointment creation to the service layer
        appointmentService.createAppointment(appointmentDto, patientName);
        // Redirect to a success page or another appropriate page
        return "redirect:/";
    }
}