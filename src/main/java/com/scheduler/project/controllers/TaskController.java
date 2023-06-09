package com.scheduler.project.controllers;

import com.scheduler.project.DTO.TaskEditDTO;
import com.scheduler.project.DTO.TaskMainDTO;
import com.scheduler.project.DTO.TaskSelectDTO;
import com.scheduler.project.services.taskServices.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.scheduler.project.services.taskServices.CompleteTaskService.CompleteTaskServiceException;
import com.scheduler.project.services.taskServices.SelectTasksService.SelectTaskServiceException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final CreateTaskService createTaskService;
    private final EditTaskService editTaskService;
    private final DeleteTaskService deleteTaskService;
    private final AddTagToTaskService addTagToTaskService;
    private final CompleteTaskService completeTaskService;
    private final SelectTasksService selectTasksService;
    @Autowired
    public TaskController(CreateTaskService createTaskService, EditTaskService editTaskService,
                          DeleteTaskService deleteTaskService, AddTagToTaskService addTagToTaskService,
                          CompleteTaskService completeTaskService, SelectTasksService selectTasksService) {
        this.createTaskService = createTaskService;
        this.editTaskService = editTaskService;
        this.deleteTaskService = deleteTaskService;
        this.addTagToTaskService = addTagToTaskService;
        this.completeTaskService = completeTaskService;
        this.selectTasksService = selectTasksService;
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
    @GetMapping("/getAllUserTasks")
    public ResponseEntity<?> getUserTasks(@RequestParam(name = "user_id") @NotNull Long userId)
    {
        try {
            List<TaskSelectDTO> result = selectTasksService.selectAllUserTasks(userId);
            return ResponseEntity.ok().body(result);
        }
        catch (SelectTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTaskById")
    public ResponseEntity<?> getTask(@RequestParam(name = "task_id") @NotNull Long taskId)
    {
        try {
            TaskSelectDTO result = selectTasksService.selectTaskById(taskId);
            return ResponseEntity.ok().body(result);
        }
        catch (SelectTaskServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
