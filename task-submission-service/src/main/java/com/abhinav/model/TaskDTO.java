package com.abhinav.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
     private Long id;
    private String title;
    private String description;
    private String image;       
    private Long assignedUserId;
    private List<String> tags = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private TaskStatus status;
}
