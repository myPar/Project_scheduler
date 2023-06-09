package com.scheduler.project.services.taskServices;

import com.scheduler.project.entities.CompletedTaskDataEntity;
import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.mappers.TaskMapper;
import com.scheduler.project.repos.CompletedTaskDataRepo;
import com.scheduler.project.repos.TaskRepo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompleteTaskService {
    @NoArgsConstructor
    public static class CompleteTaskServiceException extends Exception {
        public CompleteTaskServiceException(String message) {
            super(message);
        }
    }
    private final TaskRepo taskRepo;
    private final CompletedTaskDataRepo completedTaskDataRepo;

    public CompleteTaskService(TaskRepo taskRepo, CompletedTaskDataRepo completedTaskDataRepo) {
        this.taskRepo = taskRepo;
        this.completedTaskDataRepo = completedTaskDataRepo;
    }

    public void completeTask(Long task_id) throws CompleteTaskServiceException {
        Optional<TaskEntity> optionalTask = taskRepo.findById(task_id);

        if (optionalTask.isEmpty()) {
            throw new CompleteTaskServiceException("no task with id=" + task_id);
        }
        TaskEntity task = optionalTask.get();
        task.setCompleted(true);

        CompletedTaskDataEntity completedTaskDataEntity = TaskMapper.INSTANCE.getCompletionDataFromTask(task);

        completedTaskDataRepo.save(completedTaskDataEntity);
        taskRepo.save(task);
    }
}
