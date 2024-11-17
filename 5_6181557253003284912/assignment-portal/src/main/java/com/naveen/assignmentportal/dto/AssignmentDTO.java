package com.naveen.assignmentportal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignmentDTO {
    @NotBlank(message = "UserId is required.")
    private String userId;

    @NotBlank(message = "Task is required.")
    private String task;

    @NotBlank(message = "Admin is required.")
    private String admin;
}
