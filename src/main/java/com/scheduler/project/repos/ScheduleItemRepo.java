package com.scheduler.project.repos;

import com.scheduler.project.entities.ScheduleItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleItemRepo extends CrudRepository<ScheduleItemEntity, Long> {
}
