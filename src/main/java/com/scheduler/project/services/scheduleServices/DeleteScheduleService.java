package com.scheduler.project.services.scheduleServices;

import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.repos.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteScheduleService {
    public static class DeleteScheduleServiceException extends Exception {
        public DeleteScheduleServiceException(String msg) {super(msg);}
    }
    private ScheduleRepo scheduleRepo;

    @Autowired
    public DeleteScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }
    public void deleteSchedule(Long id) throws DeleteScheduleServiceException {
        Optional<ScheduleEntity> schedule = scheduleRepo.findById(id);
        if (schedule.isEmpty()) {
            throw new DeleteScheduleServiceException("no task with id=" + id);
        }
        scheduleRepo.deleteById(id);
    }
}
