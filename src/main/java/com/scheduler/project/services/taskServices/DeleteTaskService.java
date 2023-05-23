package com.scheduler.project.services.taskServices;

import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.repos.TaskRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DeleteTaskService {
    @NoArgsConstructor
    public static class DeleteTaskServiceException extends Exception {
        public DeleteTaskServiceException(String message) {
            super(message);
        }
    }
    private final TaskRepo taskRepo;

    @Autowired
    public DeleteTaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }
    public void deleteTask(Long id) throws DeleteTaskServiceException {
        Optional<TaskEntity> task = taskRepo.findById(id);
        if (task.isEmpty()) {
            throw new DeleteTaskServiceException("no task with id=" + id);
        }
        taskRepo.deleteTask(id);
    }
}
