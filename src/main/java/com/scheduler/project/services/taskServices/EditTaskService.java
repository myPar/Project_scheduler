package com.scheduler.project.services.taskServices;
import com.scheduler.project.DTO.TaskEditDTO;
import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.mappers.TaskMapper;
import com.scheduler.project.repos.TaskRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class EditTaskService {
    @NoArgsConstructor
    public static class EditTaskServiceException extends Exception {
        public EditTaskServiceException(String message) {
            super(message);
        }
    }
    private final TaskRepo taskRepo;

    @Autowired
    public EditTaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public TaskEntity editTask(TaskEditDTO taskEditDTO) throws EditTaskServiceException {
        Long task_id = taskEditDTO.getTask_id();
        Optional<TaskEntity> editingTaskOptional = taskRepo.findById(task_id);

        if (editingTaskOptional.isEmpty()) {
            throw new EditTaskServiceException("there is no task with task_id=" + task_id);
        }
        TaskEntity editingTask = editingTaskOptional.get();
        try {
            taskEditDTO.initStartTimeValue();
            TaskMapper.INSTANCE.updateTaskFromDTO(taskEditDTO, editingTask);
        } catch (DateTimeParseException e) {
            throw new EditTaskServiceException("invalid start time string - " +
                    taskEditDTO.getStart_time() + ", use pattern - 'yyyy-MM-dd HH:mm'");
        }
        if (editingTask.isOverdue()) {
            editingTask.setOverdue(true);
        }
        else if (editingTask.getOverdue()) {
            editingTask.setOverdue(false);    // set overdue back to false if now is not overdue
        }
        return taskRepo.save(editingTask);
    }
}
