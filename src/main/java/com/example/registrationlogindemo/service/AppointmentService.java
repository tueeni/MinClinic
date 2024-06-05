package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.AppointmentDto;
import com.example.registrationlogindemo.entity.Appointment;

import java.time.LocalDateTime;

public interface AppointmentService {
    void createAppointment(AppointmentDto appointmentDto, String patientName);
    //TODO: createAppointment
    // getAppointment
    //cancelAppointment
    //confirmAppointment
    //updateAppointmentStatus ??
    //findAvailableTimeSlots
    //updateAppointmentDetails
}
