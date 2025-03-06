package com.abhinav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.model.Submission;
import java.util.List;


public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    
    List<Submission> findByTaskId(Long taskId);
}
