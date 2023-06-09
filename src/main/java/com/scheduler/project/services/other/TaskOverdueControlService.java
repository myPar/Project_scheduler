package com.scheduler.project.services.other;

import com.scheduler.project.entities.TaskEntity;
import com.scheduler.project.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskOverdueControlService {
    private final TaskRepo taskRepo;

    @Autowired
    public TaskOverdueControlService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }
    public Collection<TaskEntity> overdueUpdate(Collection<TaskEntity> taskEntities) {
        for (TaskEntity taskEntity: taskEntities) {

            if (taskEntity.checkOverdue()) {
                taskEntity.setOverdue(true);
                taskRepo.save(taskEntity);
            }
        }
        return taskEntities;
    }
}
