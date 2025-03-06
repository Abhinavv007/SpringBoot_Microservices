package com.abhinav.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.model.Submission;
import com.abhinav.model.TaskDTO;
import com.abhinav.repository.SubmissionRepository;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskService taskService;


    public Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception {
        TaskDTO task = taskService.getTaskById(taskId, jwt);
        if (task == null) {
            throw new Exception("Task not found");
        }
        Submission submission = new Submission();   
        submission.setTaskId(taskId);
        submission.setGithubLink(githubLink);
        submission.setUserId(userId);
        submission.setSubmissionTime(LocalDateTime.now());
        return submissionRepository.save(submission);
    }

    public Submission getSubmissionById(Long id) throws Exception {
        return submissionRepository.findById(id).orElseThrow(() -> new Exception("Submission not found"));
    }

    public List<Submission> getAllTaskSubmission(){
        List<Submission> submissions = submissionRepository.findAll();
        return submissions;
    }

    public List<Submission> getTaskSubmissionById(Long id){
        List<Submission> submissions = submissionRepository.findByTaskId(id);
        return submissions;
    }

   public Submission acceptDeclineSubmission(Long id, String status, String jwt) throws Exception {
        Submission submission = getSubmissionById(id);
        submission.setStatus(status);
        if ("ACCEPT".equalsIgnoreCase(status)) {
            taskService.completeTask(submission.getTaskId(), jwt);
        }
        return submissionRepository.save(submission);
    }  

}
