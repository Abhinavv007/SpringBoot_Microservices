package com.abhinav.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.abhinav.model.TaskDTO;

@FeignClient(name="TASK-SERVICE",url="http://localhost:5002")
public interface TaskService {  
 
   @GetMapping("/api/tasks/{id}")
   TaskDTO getTaskById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception;

    @PutMapping("/api/tasks/{id}/complete")
    TaskDTO completeTask(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception;

}
