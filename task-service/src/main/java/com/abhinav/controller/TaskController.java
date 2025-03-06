package com.abhinav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.model.Task;
import com.abhinav.model.TaskStatus;
import com.abhinav.model.UserDTO;
import com.abhinav.service.TaskService;
import com.abhinav.service.UserService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt)
            throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task createdTask = taskService.createTask(task, user.getRole());
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, @RequestHeader("Authorization") String jwt)
            throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(@RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.assignedUsersTask(user.getId(), status);
        return ResponseEntity.ok(tasks);
    }

    
    @GetMapping("/allTasks")
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) TaskStatus status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Task> tasks = taskService.getAllTasks(status);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{id}/{userId}/assign")
    public ResponseEntity<Task> assignTask(@PathVariable Long id, @PathVariable Long userId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.assignToUser(userId, id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}/{userId}/update")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task task,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task updatedTask = taskService.updateTask(id, task, user.getId());
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Task task = taskService.completeTask(id);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteTask(@PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        taskService.deleteTask(id);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
