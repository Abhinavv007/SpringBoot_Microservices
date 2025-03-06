package com.abhinav.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.model.Submission;
import com.abhinav.model.UserDTO;
import com.abhinav.service.SubmissionService;
import com.abhinav.service.TaskService;
import com.abhinav.service.UserService;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
    
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/submit")
    public ResponseEntity<Submission> submitTask(@RequestParam Long taskId, @RequestParam String githubLink, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(taskId, githubLink, user.getId(), jwt);
        return ResponseEntity.ok(submission);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/getAllSubmissions")
    public ResponseEntity<List<Submission>> getAllSubmissions(@RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getAllTaskSubmission();
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<List<Submission>> getTaskSubmissionById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Submission> submissions = submissionService.getTaskSubmissionById(id);
        return ResponseEntity.ok(submissions);
    }

    @PutMapping("/accept/decline/{id}")
    public ResponseEntity<Submission> acceptDeclineSubmission(@PathVariable Long id, @RequestParam String status, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Submission submissions = submissionService.acceptDeclineSubmission(id, status, jwt);
        return ResponseEntity.ok(submissions);
    }
}
