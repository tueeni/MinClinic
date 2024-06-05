package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.DoctorScheduleDto;
import com.example.registrationlogindemo.entity.Appointment;
import com.example.registrationlogindemo.entity.DoctorSchedule;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.AppointmentRepository;
import com.example.registrationlogindemo.repository.DoctorScheduleRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.DoctorScheduleService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorScheduleServiceImpl(DoctorScheduleRepository doctorScheduleRepository, UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }
    @Override
    public DoctorScheduleDto createDoctorSchedule(DoctorScheduleDto doctorScheduleDTO, Long doctorId) {
        User doctor = userRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Врач с указанным идентификатором не найден"));

        DoctorSchedule doctorSchedule = new DoctorSchedule();
        doctorSchedule.setDoctor(doctor);
        doctorSchedule.setDayOfWeek(doctorScheduleDTO.getDayOfWeek());
        doctorSchedule.setStartTime(doctorScheduleDTO.getStartTime());
        doctorSchedule.setEndTime(doctorScheduleDTO.getEndTime());

        doctorSchedule = doctorScheduleRepository.save(doctorSchedule);

        return convertEntityToDto(doctorSchedule);
    }
    @Override
//    public List<DoctorScheduleDto> getDoctorScheduleForLoggedInDoctor(Principal principal) {
//        // Retrieve the currently logged-in doctor's username
//        String name = principal.getName();
//
//        // Retrieve the doctor entity from the database using the username
//        User doctor = userRepository.findByName(name);
//
//        if (doctor != null) {
//            List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findByDoctorId(doctor.getId());
//
//            return doctorSchedules.stream()
//                    .map(this::convertEntityToDto)
//                    .collect(Collectors.toList());
//        } else {
//            // Handle case where doctor is not found
//            return Collections.emptyList(); // Or throw an exception, depending on your requirements
//        }
//    }

    public List<DoctorScheduleDto> getDoctorScheduleForLoggedInDoctor(Principal principal) {
        System.out.println(doctorScheduleRepository.findByDoctorId(6L));
        // Retrieve the currently logged-in doctor's username
        String name = principal.getName();

        // Retrieve the doctor entity from the database using the username
        User doctor = userRepository.findByEmail(name);

        if (doctor != null) {
            List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findByDoctorId(doctor.getId());

            return doctorSchedules.stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        } else {
            // Handle case where doctor is not found
            return Collections.emptyList();
        }
    }

    @Override
    public List<DoctorScheduleDto> getDoctorScheduleByDoctorId(Long doctorId) {
        List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findByDoctorId(doctorId);
        return doctorSchedules.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isTimeSlotAvailable(Long doctorId, LocalDateTime dateTime) {
        List<DoctorSchedule> doctorSchedules = doctorScheduleRepository.findByDoctorId(doctorId);

        // Проверить, есть ли записи о приемах на указанное время
        for (DoctorSchedule schedule : doctorSchedules) {
            LocalDateTime startTime = LocalDateTime.of(dateTime.toLocalDate(), schedule.getStartTime());
            LocalDateTime endTime = LocalDateTime.of(dateTime.toLocalDate(), schedule.getEndTime());

            // Проверить, попадает ли время приема в интервал времени расписания
            if (dateTime.isAfter(startTime) && dateTime.isBefore(endTime)) {
                // Если на это время уже есть запись о приеме, вернуть false
                List<Appointment> appointments = appointmentRepository.findByDoctorIdAndDateTime(doctorId, dateTime);
                if (!appointments.isEmpty()) {
                    return false;
                }
            }
        }

        // Если ни одна из записей о приемах не перекрывается с указанным временем, вернуть true
        return true;
    }



    private DoctorScheduleDto convertEntityToDto(DoctorSchedule doctorSchedule) {
        DoctorScheduleDto doctorScheduleDto = new DoctorScheduleDto();
        doctorScheduleDto.setId(doctorSchedule.getId());
        doctorScheduleDto.setDoctorId(doctorSchedule.getDoctor().getId());
        doctorScheduleDto.setDayOfWeek(doctorSchedule.getDayOfWeek());
        doctorScheduleDto.setStartTime(doctorSchedule.getStartTime());
        doctorScheduleDto.setEndTime(doctorSchedule.getEndTime());
        return doctorScheduleDto;
    }

}