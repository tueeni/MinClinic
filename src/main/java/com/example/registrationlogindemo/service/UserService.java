package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
    void saveDoctor(UserDto userDto, String specialization, String contactNumber);
    void savePatient(UserDto userDto, String contactNumber);
    List<User> findDoctors();

    }
