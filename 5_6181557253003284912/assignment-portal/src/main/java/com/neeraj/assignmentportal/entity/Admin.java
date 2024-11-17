package com.neeraj.assignmentportal.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "admins")
public class Admin {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
}
