package com.scheduler.project.services.taskServices;

import com.scheduler.project.DTO.TaskSelectDTO;
import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.entities.UserEntity;
import com.scheduler.project.mappers.TaskMapper;
import com.scheduler.project.repos.TaskRepo;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SelectTasksService {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    public SelectTasksService(TaskRepo taskRepo, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    @NoArgsConstructor
    public static class SelectTaskServiceException extends Exception {
        public SelectTaskServiceException(String message) {
            super(message);
        }
    }

    private List<TaskEntity> getTasksByIds(List<Long> ids) {
        List<TaskEntity> result = new ArrayList<>();

        for (Long id: ids) {
            result.add(taskRepo.selectTaskById(id).get());
        }
        return result;
    }

    public List<TaskSelectDTO> selectAllUserTasks(Long userId) throws SelectTaskServiceException {
        Optional<UserEntity> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()) {
            throw new SelectTaskServiceException("no user with id=" + userId);
        }
        List<Long> selectedTasksIds = taskRepo.selectUserTasksIds(userId);

        return TaskMapper.INSTANCE.getTasksSelectDTOFromTaskEntities(getTasksByIds(selectedTasksIds));
    }

    public TaskSelectDTO selectTaskById(Long taskId) throws SelectTaskServiceException {
        Optional<TaskEntity> taskOptional = taskRepo.selectTaskById(taskId);

        if (taskOptional.isEmpty()) {
            throw new SelectTaskServiceException("no task with id=" + taskId);
        }
        TaskEntity selectedTask = taskOptional.get();

        return TaskMapper.INSTANCE.getTaskSelectDTOFromEntity(selectedTask);
    }
}
