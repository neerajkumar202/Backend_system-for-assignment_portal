package com.neeraj.assignmentportal.repository;

import com.neeraj.assignmentportal.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findAdminByName(String name);
    Optional<Admin> findByEmail(String email);
}
