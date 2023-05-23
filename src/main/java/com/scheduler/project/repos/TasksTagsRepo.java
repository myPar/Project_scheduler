package com.scheduler.project.repos;

import com.scheduler.project.entities.TasksTagsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface TasksTagsRepo extends CrudRepository<TasksTagsEntity, Long> {
    @Transactional
    @Modifying
    @Query(value="INSERT INTO tasks_tags (task_id, tag_id) VALUES(:task_id, :tag_id)", nativeQuery = true)
    void insertItem(@Param(value = "tag_id") Long tag_id, @Param(value = "task_id") Long task_id);

    @Query(value="SELECT * FROM tasks_tags t WHERE t.tag_id=:tag_id AND t.task_id =:task_id", nativeQuery = true)
    Collection<TasksTagsEntity> checkTaskHasTag(@Param(value="task_id") Long task_id, @Param(value = "tag_id") Long tag_id);
}
