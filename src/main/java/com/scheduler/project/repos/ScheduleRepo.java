package com.scheduler.project.repos;

import com.scheduler.project.entities.ScheduleEntity;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepo extends CrudRepository<ScheduleEntity, Long> {
}
