package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);
}
