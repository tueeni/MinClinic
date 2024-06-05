package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.DoctorScheduleDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.DoctorScheduleService;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DayOfWeek;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final DoctorScheduleService doctorScheduleService;


    public AdminController(UserService userService, DoctorScheduleService doctorScheduleService) {
        this.userService = userService;
        this.doctorScheduleService = doctorScheduleService;
    }

    @GetMapping("/")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/users"; // Вернуть имя шаблона Thymeleaf
    }

    @GetMapping("/newSchedule")
    public String showCreateForm(Model model) {
        DoctorScheduleDto doctorScheduleDto = new DoctorScheduleDto();
        model.addAttribute("doctorScheduleDto", doctorScheduleDto);

        List<User> doctors = userService.findDoctors(); // Получаем список врачей
        model.addAttribute("doctors", doctors);

        return "admin/scheduleForm";
    }

    @PostMapping("/newSchedule")
    public String createSchedule(@ModelAttribute("doctorScheduleDto") DoctorScheduleDto doctorScheduleDto) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(String.valueOf(doctorScheduleDto.getDayOfWeek()));
        doctorScheduleDto.setDayOfWeek(dayOfWeek);
        doctorScheduleService.createDoctorSchedule(doctorScheduleDto, doctorScheduleDto.getDoctorId());
        return "redirect:/admin/newSchedule";
    }
}
