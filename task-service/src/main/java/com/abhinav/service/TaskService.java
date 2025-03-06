package com.abhinav.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinav.model.Task;
import com.abhinav.model.TaskStatus;
import com.abhinav.repository.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task, String requesterRole) throws Exception{
        if(!requesterRole.equals("ROLE_ADMIN")){
            throw new Exception("Only ADMIN can create a task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());

            return taskRepository.save(task);
        }
    
        public Task getTaskById(Long taskId) throws Exception {
            return taskRepository.findById(taskId).orElseThrow(() -> new Exception("Task not found"));
        }

        public List<Task> getAllTasks(TaskStatus status) {
            List<Task> allTasks = taskRepository.findAll();
            List<Task> filteredTasks = allTasks.stream().filter(task -> status == null || task.getStatus() == status).collect(Collectors.toList());
            return filteredTasks;
        }

        public Task updateTask(Long taskId, Task task, Long userId) throws Exception {
            Task existingTask = taskRepository.findById(taskId).orElseThrow(() -> new Exception("Task not found"));
            if (task.getTitle() != null) {
                existingTask.setTitle(task.getTitle());
            }
            if (task.getDescription() != null) {
                existingTask.setDescription(task.getDescription());
            }
            if (task.getImage() != null) {
                existingTask.setImage(task.getImage());
            }
            if (task.getAssignedUserId() != null) {
                existingTask.setAssignedUserId(task.getAssignedUserId());
            }
            if (task.getTags() != null) {
                existingTask.setTags(task.getTags());
            }
            if (task.getStatus() != null) {
                existingTask.setStatus(task.getStatus());
            }
            if(task.getDeadline() != null){
                existingTask.setDeadline(task.getDeadline());
            }
            return taskRepository.save(existingTask);
        }

        public void deleteTask(Long taskId) throws Exception {
            getTaskById(taskId);
            taskRepository.deleteById(taskId);
        }

        public Task assignToUser(Long userId, Long taskId) throws Exception {
            Task task = getTaskById(taskId);
            task.setAssignedUserId(userId);
            task.setStatus(TaskStatus.PENDING);
            return taskRepository.save(task); 

        }

        public List<Task> assignedUsersTask(Long userId,TaskStatus status){
            List<Task> allTasks = taskRepository.findByAssignedUserId(userId);
            List<Task> filteredTasks = allTasks.stream().filter(task -> status ==null || task.getStatus() == status).collect(Collectors.toList());
            return filteredTasks; 
        }

        public Task completeTask(Long taskId) throws Exception{
            Task task = getTaskById(taskId);
            task.setStatus(TaskStatus.DONE);
            return taskRepository.save(task);
        }
    }

