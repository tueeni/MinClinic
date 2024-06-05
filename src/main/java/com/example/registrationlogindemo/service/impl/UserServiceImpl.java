package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());

        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        userDto.setRoles(roles);
        return userDto;
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public void saveDoctor(UserDto userDto, String specialization, String contactNumber) {
        User doctor = new User();
        doctor.setName(userDto.getFirstName() + " " + userDto.getLastName());
        doctor.setEmail(userDto.getEmail());
        doctor.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role doctorRole = roleRepository.findByName("ROLE_DOCTOR");
        if (doctorRole == null) {
            doctorRole = new Role();
            doctorRole.setName("ROLE_DOCTOR");
            roleRepository.save(doctorRole);
        }
        doctor.setRoles(Arrays.asList(doctorRole));
        doctor.setSpecialization(userDto.getSpecialization());
        doctor.setContactNumber(userDto.getContactNumber());
        userRepository.save(doctor);
    }
    @Override
    public void savePatient(UserDto userDto, String contactNumber) {
        User patient = new User();
        patient.setName(userDto.getFirstName() + " " + userDto.getLastName());
        patient.setEmail(userDto.getEmail());
        patient.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role patientRole = roleRepository.findByName("ROLE_PATIENT");
        if (patientRole == null) {
            patientRole = new Role();
            patientRole.setName("ROLE_PATIENT");
            roleRepository.save(patientRole);
        }
        patient.setRoles(Arrays.asList(patientRole));
        patient.setContactNumber(userDto.getContactNumber());
        userRepository.save(patient);
    }

    @Override
    public List<User> findDoctors() {
        return userRepository.findByRolesName("ROLE_DOCTOR");
    }
}
