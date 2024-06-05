package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.DoctorScheduleDto;
import com.example.registrationlogindemo.entity.DoctorSchedule;
import com.example.registrationlogindemo.entity.User;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorScheduleService {
    DoctorScheduleDto createDoctorSchedule(DoctorScheduleDto doctorScheduleDTO, Long doctorId);
    List<DoctorScheduleDto> getDoctorScheduleForLoggedInDoctor(Principal principal);
    List<DoctorScheduleDto> getDoctorScheduleByDoctorId(Long doctorId);
    boolean isTimeSlotAvailable(Long doctorId, LocalDateTime dateTime);

//  List<DoctorScheduleDto> getAvailableTimeSlots(User doctor);
//    void addTimeSlot(DoctorScheduleDto doctorScheduleDto);
//    void updateTimeSlot(DoctorScheduleDto doctorScheduleDto);
//    void deleteTimeSlot(Long id);
//    boolean checkAvailability(DoctorScheduleDto doctorScheduleDto);

}
