package com.scheduler.project.controllers;

import com.scheduler.project.DTO.TaskEditDTO;
import com.scheduler.project.DTO.TaskMainDTO;
import com.scheduler.project.services.taskServices.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scheduler.project.services.taskServices.CompleteTaskService.CompleteTaskServiceException;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final CreateTaskService createTaskService;
    private final EditTaskService editTaskService;
    private final DeleteTaskService deleteTaskService;
    private final AddTagToTaskService addTagToTaskService;
    private final CompleteTaskService completeTaskService;

    @Autowired
    public TaskController(CreateTaskService createTaskService, EditTaskService editTaskService,
                          DeleteTaskService deleteTaskService, AddTagToTaskService addTagToTaskService,
                          CompleteTaskService completeTaskService) {
        this.createTaskService = createTaskService;
        this.editTaskService = editTaskService;
        this.deleteTaskService = deleteTaskService;
        this.addTagToTaskService = addTagToTaskService;
        this.completeTaskService = completeTaskService;
    }

    @GetMapping("/get")
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
    @PutMapping("/modify")
    public ResponseEntity<?> modifyTask(@RequestBody @Valid TaskEditDTO taskMainDTO) {
        try {
            editTaskService.editTask(taskMainDTO);
            return ResponseEntity.ok().body("task was edited");
        }
        catch (EditTaskService.EditTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskMainDTO requestTaskDTO) {
        try {
            createTaskService.createTask(requestTaskDTO);
            return ResponseEntity.ok().body("task was created");
        }
        catch (CreateTaskService.CreateTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/remove")
    public ResponseEntity<?> deleteTask(@RequestParam @NotNull Long id) {
        try {
            deleteTaskService.deleteTask(id);
            return ResponseEntity.ok("task with id=" + id + " successfully deleted");
        }
        catch (DeleteTaskService.DeleteTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/addTag")
    public ResponseEntity<?> addTagToTask(@RequestParam @NotNull Long tag_id, @RequestParam @NotNull Long task_id) {
        try {
            addTagToTaskService.addTagToTask(task_id, tag_id);

            return ResponseEntity.ok().body("tag with id=" + tag_id + " successfully added to task with id=" + task_id);
        }
        catch (AddTagToTaskService.AddTagToTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/completeTask")
    public ResponseEntity<?> completeTask(@RequestParam @NotNull Long task_id) {
        try {
            completeTaskService.completeTask(task_id);
            return ResponseEntity.ok().body("task with id=" + task_id + " was completed");
        }
        catch (CompleteTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
