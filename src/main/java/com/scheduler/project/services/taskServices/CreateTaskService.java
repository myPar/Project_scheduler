package com.scheduler.project.services.taskServices;

import com.scheduler.project.DTO.TaskMainDTO;
import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.entities.UserEntity;
import com.scheduler.project.mappers.TaskMapper;
import com.scheduler.project.repos.TaskRepo;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeParseException;
import java.util.Optional;


@Service
public class CreateTaskService {
    @NoArgsConstructor
    public static class CreateTaskServiceException extends Exception {
        public CreateTaskServiceException(String message) {
            super(message);
        }
    }
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Autowired
    public CreateTaskService(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public TaskEntity createTask(TaskMainDTO taskMainDTO) throws CreateTaskServiceException {
        Long user_id = taskMainDTO.getUser_id();
        Optional<UserEntity> user = userRepo.findById(user_id);

        // check user existence
        if (user.isEmpty()) {
            throw new CreateTaskServiceException("no user with id=" + user_id + ", task should correspond to the existing user");
        }
        Long start_time_value;
        try {
            start_time_value = taskMainDTO.getStartTimeValue();
        } catch (DateTimeParseException e) {
            throw new CreateTaskServiceException("invalid start time string - " + taskMainDTO.getStart_time() + ", use pattern - 'yyyy-MM-dd HH:mm'");
        }
        TaskEntity task = TaskEntity.builder()
                .task_name(taskMainDTO.getTask_name())
                .user(user.get())
                .difficult(taskMainDTO.getDifficult())
                .completed(false)
                .start_time(start_time_value)
                .description(taskMainDTO.getDescription())
                .duration(taskMainDTO.getDuration()).build();

        return taskRepo.save(task);
    }
}
