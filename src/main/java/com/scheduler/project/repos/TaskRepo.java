package com.scheduler.project.repos;

import com.scheduler.project.entities.TaskEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<TaskEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from tasks t where t.id=:id", nativeQuery = true)
    void deleteTask(@Param("id") Long id);


    @Query(value="select id from tasks where tasks.user_id= :userId",
            nativeQuery = true)
    List<Long> selectUserTasksIds(@RequestParam(name = "userId") Long userId);

    @Query(value="select * from tasks left outer join \n" +
            "(select task_id, tag_name from tasks_tags inner join (select * from tags) tt \n" +
            "on tt.id = tasks_tags.tag_id) t \n" +
            "on tasks.id=t.task_id where tasks.id= :taskId", nativeQuery = true)
    Optional<TaskEntity> selectTaskById(@Param(value = "taskId") Long taskId);
}
