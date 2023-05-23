package com.scheduler.project.repos;

import com.scheduler.project.entities.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskRepo extends CrudRepository<TaskEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from tasks t where t.id=:id", nativeQuery = true)
    void deleteTask(@Param("id") Long id);
}
