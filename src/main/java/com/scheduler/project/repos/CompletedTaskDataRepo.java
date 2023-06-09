package com.scheduler.project.repos;

import com.scheduler.project.entities.CompletedTaskDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletedTaskDataRepo extends JpaRepository<CompletedTaskDataEntity, Long> {
}
