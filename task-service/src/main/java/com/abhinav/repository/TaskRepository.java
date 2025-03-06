package com.abhinav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.model.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
    
    List<Task> findByAssignedUserId(Long assignedUserId);
}
