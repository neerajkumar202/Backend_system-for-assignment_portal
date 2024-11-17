package com.neeraj.assignmentportal.service;

import com.neeraj.assignmentportal.dto.AssignmentDTO;
import com.neeraj.assignmentportal.dto.LoginDTO;
import com.neeraj.assignmentportal.dto.RegisterDTO;
import com.neeraj.assignmentportal.dto.Response;
import com.neeraj.assignmentportal.entity.Admin;
import com.neeraj.assignmentportal.entity.Assignment;
import com.neeraj.assignmentportal.entity.Status;
import com.neeraj.assignmentportal.entity.User;
import com.neeraj.assignmentportal.repository.AdminRepository;
import com.neeraj.assignmentportal.repository.AssignmentRepository;
import com.neeraj.assignmentportal.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final AssignmentRepository assignmentRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository, AssignmentRepository assignmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public Response register(RegisterDTO dto, String role) {
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            return Response.builder().statusCode(400).message("User already exists").data(List.of(existingUser.get().getName())).build();
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        userRepository.save(user);
        return Response.builder().statusCode(201).message("User registered successfully").build();
    }

    public Response login(LoginDTO dto) {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty() || !passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
            return Response.builder().statusCode(401).message("Invalid credentials").build();
        }

        return Response.builder().statusCode(200).message("Login successful").build();
    }

    public Response uploadAssignment(AssignmentDTO dto) {
        Optional<User> userExists = userRepository.findById(dto.getUserId());
        if (userExists.isEmpty()) {
            return Response.builder().statusCode(404).message("User with ID [" + dto.getUserId() + "] not found.").build();
        }

        Optional<Admin> adminExists = adminRepository.findAdminByName(dto.getAdmin());
        if (adminExists.isEmpty()) {
            return Response.builder().statusCode(404).message("Admin with name [" + dto.getAdmin() + "] not found or not an admin.").build();
        }

        Assignment assignment = new Assignment();
        assignment.setUserId(dto.getUserId());
        assignment.setTask(dto.getTask());
        assignment.setAdmin(dto.getAdmin());
        assignment.setStatus(Status.PENDING);

        assignmentRepository.save(assignment);

        return Response.builder().statusCode(201).message("Assignment uploaded successfully").build();
    }

    public Response getAllAdmins(int pageNumber, int pageSize) {
        Page<User> adminUsers = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<User> adminUsersList = adminUsers.getContent().stream().filter(user -> "ADMIN".equalsIgnoreCase(user.getRole())).collect(Collectors.toList());
        return Response.builder()
                .statusCode(200)
                .message("List of admin users")
                .data(adminUsersList)
                .build();
    }
}
