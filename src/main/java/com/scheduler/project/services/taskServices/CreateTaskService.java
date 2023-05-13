package com.scheduler.project.services.taskServices;

import com.scheduler.project.DTO.TaskRequestDTO;
import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.entities.UserEntity;
import com.scheduler.project.repos.TaskRepo;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
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

    public TaskEntity createTask(TaskRequestDTO taskRequestDTO) throws CreateTaskServiceException {
        Long user_id = taskRequestDTO.getUser_id();
        Optional<UserEntity> user = userRepo.findById(user_id);

        // check user existence
        if (user.isEmpty()) {
            throw new CreateTaskServiceException("no user with is=" + user_id + ", task should correspond to the existing user");
        }
        // check start time
        String start_time = taskRequestDTO.getStart_time();
        Long start_time_value;
        try {
            Date date = taskRequestDTO.getStartTimeDate(ZoneId.systemDefault().toString());
            start_time_value = date.getTime();
        }
        catch (ParseException e) {
            throw new CreateTaskServiceException("invalid start time string - " + start_time + ", use pattern - 'yyyy-MM-dd HH:mm'");
        }
        TaskEntity task = TaskEntity.builder()
                .task_name(taskRequestDTO.getTask_name())
                .user(user.get())
                .difficult(taskRequestDTO.getDifficult())
                .completed(false)
                .start_time(start_time_value)
                .description(taskRequestDTO.getDescription())
                .duration(taskRequestDTO.getDuration()).build();

        return taskRepo.save(task);
    }
}
