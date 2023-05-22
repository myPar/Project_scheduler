package com.scheduler.project.controllers;

import com.scheduler.project.DTO.TaskMainDTO;
import com.scheduler.project.DTO.TaskEditDTO;
import com.scheduler.project.services.taskServices.CreateTaskService;
import com.scheduler.project.services.taskServices.CreateTaskService.CreateTaskServiceException;
import com.scheduler.project.services.taskServices.EditTaskService;
import com.scheduler.project.services.taskServices.EditTaskService.EditTaskServiceException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CreateTaskService createTaskService;
    private final EditTaskService editTaskService;

    @Autowired
    public UserController(CreateTaskService createTaskService, EditTaskService editTaskService) {
        this.createTaskService = createTaskService;
        this.editTaskService = editTaskService;
    }

    @GetMapping("/tasks/get")
    public ResponseEntity<?> getUserTasks(
            @RequestParam(name="id") String userId, @RequestParam(name="duration", required=false) String minDuration,
            @RequestParam(name="difficult", required=false) String difficult, @RequestParam(name="time", required=false) String startTime,
            @RequestParam(name="tags", required=false) List<String> tagsList)
    {
        try {
            return null;
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("invalid request for get user tasks service");
        }
    }
    @PutMapping("tasks/modify")
    public ResponseEntity<?> modifyTask(@RequestBody @Valid TaskEditDTO taskMainDTO) {
        try {
            editTaskService.editTask(taskMainDTO);
            return ResponseEntity.ok().body("task was edited");
        }
        catch (EditTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/tasks/add")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskMainDTO requestTaskDTO) {
        try {
            createTaskService.createTask(requestTaskDTO);
            return ResponseEntity.ok().body("task was created");
        }
        catch (CreateTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
