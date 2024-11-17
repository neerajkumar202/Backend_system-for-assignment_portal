package com.naveen.assignmentportal.service;


import com.naveen.assignmentportal.dto.LoginDTO;
import com.naveen.assignmentportal.dto.RegisterDTO;
import com.naveen.assignmentportal.dto.Response;
import com.naveen.assignmentportal.entity.Admin;
import com.naveen.assignmentportal.entity.Assignment;
import com.naveen.assignmentportal.entity.Status;
import com.naveen.assignmentportal.repository.AdminRepository;
import com.naveen.assignmentportal.repository.AssignmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentService.class);
    private final String CLASS_NAME = AssignmentService.class.getSimpleName();

    private final AdminRepository adminRepository;
    private final AssignmentRepository assignmentRepository;
    private final PasswordEncoder passwordEncoder;

    public AssignmentService(AdminRepository adminRepository, AssignmentRepository assignmentRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.assignmentRepository = assignmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Response register(RegisterDTO dto, String role) {
        log.info("[{}] Registering new Admin with name [{}]", CLASS_NAME, dto.getName() );
        Optional<Admin> existingUser = adminRepository.findByEmail(dto.getEmail());

        if (existingUser.isPresent()) {
            return Response.builder().statusCode(400).message("User already exists").data(List.of(existingUser.get().getName())).build();
        }

        Admin user = new Admin();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        log.info("[{}] registering new admin with name [{}]", CLASS_NAME, dto.getName());
        adminRepository.save(user);
        return Response.builder().statusCode(201).message("User registered successfully").build();
    }

    public Response login(LoginDTO dto) {
        Optional<Admin> user = adminRepository.findByEmail(dto.getEmail());
        if (user.isEmpty() || !passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
            return Response.builder().statusCode(401).message("Invalid credentials").build();
        }

        return Response.builder().statusCode(200).message("Login successful").build();
    }

    public Response acceptAssignment(String id) {
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(id);
        if (assignmentOpt.isEmpty()) {
            return Response.builder().statusCode(404).message("Assignment not found").build();
        }

        Assignment assignment = assignmentOpt.get();
        assignment.setStatus(Status.ACCEPTED);
        Assignment savedAssignment = assignmentRepository.save(assignment);

        return Response.builder()
                .statusCode(200)
                .message("Assignment accepted successfully")
                .data(List.of(savedAssignment))
                .build();
    }

    public Response rejectAssignment(String id) {
        Optional<Assignment> assignmentOpt = assignmentRepository.findById(id);
        if (assignmentOpt.isEmpty()) {
            return Response.builder().statusCode(404).message("Assignment not found").build();
        }

        Assignment assignment = assignmentOpt.get();
        assignment.setStatus(Status.REJECTED);
        Assignment savedAssignment = assignmentRepository.save(assignment);

        return Response.builder()
                .statusCode(200)
                .message("Assignment rejected successfully")
                .data(List.of(savedAssignment))
                .build();
    }

    public Response getAssignmentsByAdmin(String admin, int page, int size) {
        Page<Assignment> paginatedAssignments = assignmentRepository.findByAdmin(admin, PageRequest.of(page, size));

        return Response.builder()
                .statusCode(200)
                .message("Assignments fetched successfully")
                .data(paginatedAssignments.getContent())
                .page(paginatedAssignments.getNumber())
                .size(paginatedAssignments.getSize())
                .totalElements(paginatedAssignments.getTotalElements())
                .build();
    }
}