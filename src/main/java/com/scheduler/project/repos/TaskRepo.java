package com.scheduler.project.repos;

import com.scheduler.project.entities.TaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<TaskEntity, Long> {
}
