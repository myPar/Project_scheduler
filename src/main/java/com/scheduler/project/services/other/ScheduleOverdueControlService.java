package com.scheduler.project.services.other;

import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.repos.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class ScheduleOverdueControlService {
    private final ScheduleRepo scheduleRepo;

    @Autowired
    public ScheduleOverdueControlService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }
    public Collection<ScheduleEntity> overdueUpdate(Collection<ScheduleEntity> scheduleEntities) {
        for (ScheduleEntity scheduleEntity: scheduleEntities) {
            if (scheduleEntity.isOverdue()) {
                scheduleEntity.setOverdue(true);
                scheduleRepo.save(scheduleEntity);
            }
        }
        return scheduleEntities;
    }

}
