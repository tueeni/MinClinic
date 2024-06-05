package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showUserRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register/register-admin";
    }

    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "register/register-admin";
        }
        userService.saveUser(userDto);
        return "redirect:/login";
    }
    //DOCTOR

    @GetMapping("/register/doctor")
    public String showDoctorRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register/register-doctor";
    }

    @PostMapping("/register/saveDoctor")
    public String registerDoctor(@Valid @ModelAttribute("user") UserDto userDto,
                                 @RequestParam("specialization") String specialization,
                                 @RequestParam("contactNumber") String contactNumber,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "register/register-doctor";
        }
        userService.saveDoctor(userDto, specialization, contactNumber);
        return "redirect:/login";
    }

    //PATIENT

    @GetMapping("/register/patient")
    public String showPatientRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register/register-patient";
    }

    @PostMapping("/register/savePatient")
    public String registerPatient(@Valid @ModelAttribute("user") UserDto userDto,
                                 @RequestParam("contactNumber") String contactNumber,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "register/register-patient";
        }
        userService.savePatient(userDto, contactNumber);
        return "redirect:/login";
    }

}
