package com.scheduler.project.repos;

import com.scheduler.project.entities.ScheduleItemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleItemRepo extends CrudRepository<ScheduleItemEntity, Long> {
    @Query(value = "SELECT * FROM schedule_items si where si.schedule_id = :schedule_id and si.difficult = :difficult", nativeQuery = true)
    List<ScheduleItemEntity> selectScheduleItemsByDifficult(@Param(value = "difficult") Integer difficult,
                                                            @Param(value = "schedule_id")Long scheduleId);
}
