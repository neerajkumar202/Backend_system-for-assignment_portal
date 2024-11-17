package com.naveen.assignmentportal.controller;
import com.naveen.assignmentportal.dto.LoginDTO;
import com.naveen.assignmentportal.dto.RegisterDTO;
import com.naveen.assignmentportal.dto.Response;
import com.naveen.assignmentportal.repository.AdminRepository;
import com.naveen.assignmentportal.service.AssignmentService;
import com.naveen.assignmentportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AssignmentService assignmentService;

    @Autowired
    public AdminController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // Register a new admin
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody @Valid RegisterDTO dto) {
        Response response = assignmentService.register(dto, "ADMIN");
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Admin login
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid LoginDTO dto) {
        Response response = assignmentService.login(dto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/assignments")
    public ResponseEntity<Response> getAssignments(
            @RequestParam String admin,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        Response response = assignmentService.getAssignmentsByAdmin(admin, pageNumber, pageSize);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Accept an assignment
    @PostMapping("/assignments/{id}/accept")
    public ResponseEntity<Response> acceptAssignment(@PathVariable String id) {
        Response response = assignmentService.acceptAssignment(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // Reject an assignment
    @PostMapping("/assignments/{id}/reject")
    public ResponseEntity<Response> rejectAssignment(@PathVariable String id) {
        Response response = assignmentService.rejectAssignment(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}

