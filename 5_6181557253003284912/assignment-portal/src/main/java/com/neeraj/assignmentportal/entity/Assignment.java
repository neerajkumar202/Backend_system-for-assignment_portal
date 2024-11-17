package com.neeraj.assignmentportal.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "assignments")
public class Assignment {
    @Id
    private String id;

    @NotBlank(message = "UserId is required.")
    private String userId;

    @NotBlank(message = "Task is required.")
    private String task;

    @NotBlank(message = "Admin is required.")
    private String admin;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Status status = Status.PENDING;
}

