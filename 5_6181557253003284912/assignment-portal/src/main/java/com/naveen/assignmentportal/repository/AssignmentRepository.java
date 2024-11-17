package com.naveen.assignmentportal.repository;

import com.naveen.assignmentportal.entity.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {
    Page<Assignment> findByAdmin(String admin, Pageable pageable);
}
