package com.neeraj.assignmentportal.controller;

import com.neeraj.assignmentportal.dto.AssignmentDTO;
import com.neeraj.assignmentportal.dto.LoginDTO;
import com.neeraj.assignmentportal.dto.RegisterDTO;
import com.neeraj.assignmentportal.dto.Response;
import com.neeraj.assignmentportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RegisterDTO dto) {
        Response response = userService.register(dto, "USER");
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginDTO dto) {
        Response response = userService.login(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Upload an assignment
    @PostMapping("/upload")
    public ResponseEntity<Response> uploadAssignment(@RequestBody @Valid AssignmentDTO dto) {
        Response response = userService.uploadAssignment(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Fetch all admins
    @GetMapping("/admins")
    public ResponseEntity<Response> getAllAdmins(@RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "5") int pageSize) {
        Response response = userService.getAllAdmins(pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

