package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.AppointmentDto;
import com.example.registrationlogindemo.entity.Appointment;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.AppointmentRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.AppointmentService;
import com.example.registrationlogindemo.service.DoctorScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorScheduleService doctorScheduleService;
    private final UserRepository userRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorScheduleService doctorScheduleService, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorScheduleService = doctorScheduleService;
        this.userRepository = userRepository;
    }

//    public List<AppointmentDto> getAllAppointments() {
//        return appointmentRepository.findAll().stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
    private AppointmentDto convertToEntity(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setPatientId(appointment.getPatient().getId());
        appointmentDto.setDoctorId(appointment.getDoctor().getId());
        appointmentDto.setDateTime(appointment.getDateTime());
        return appointmentDto;
    }
    private Appointment convertToDto(AppointmentDto appointmentDto, User patient, User doctor) {
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDateTime(appointmentDto.getDateTime());
        return appointment;
    }


    @Override
    public void createAppointment(AppointmentDto appointmentDto, String patientName) {
        // Retrieve the currently logged-in patient
        User patient = userRepository.findByName(patientName);
        User doctor = userRepository.findById(appointmentDto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Врач с указанным идентификатором не найден"));

        // Check if the time slot is available
        boolean isTimeSlotAvailable = doctorScheduleService.isTimeSlotAvailable(appointmentDto.getDoctorId(), appointmentDto.getDateTime());

        if (!isTimeSlotAvailable) {
            throw new RuntimeException("Selected time slot is not available for the doctor.");
        }

        // Create an appointment entity
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDateTime(appointmentDto.getDateTime());

        // Save the appointment to the database
        appointmentRepository.save(appointment);
    }


}
