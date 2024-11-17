package com.naveen.assignmentportal.repository;

import com.naveen.assignmentportal.entity.Admin;
import com.naveen.assignmentportal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findAdminByName(String name);
    Optional<Admin> findByEmail(String email);
}
