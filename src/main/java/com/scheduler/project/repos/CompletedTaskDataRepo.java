package com.scheduler.project.repos;

import com.scheduler.project.entities.CompletedTaskDataEntity;
import org.springframework.data.repository.CrudRepository;

public interface CompletedTaskDataRepo extends CrudRepository<CompletedTaskDataEntity, Long> {
}
