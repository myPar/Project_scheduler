package com.scheduler.project.services.scheduleServices;


import com.scheduler.project.DTO.ScheduleSelectDTO;
import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.mappers.ScheduleMapper;
import com.scheduler.project.repos.ScheduleRepo;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelectScheduleService {
    @NoArgsConstructor
    public static class SelectScheduleServiceException extends Exception {
        public SelectScheduleServiceException(String message) {
            super(message);
        }
    }
    private final ScheduleRepo scheduleRepo;
    private final UserRepo userRepo;

    @Autowired
    public SelectScheduleService(ScheduleRepo scheduleRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.scheduleRepo = scheduleRepo;
    }

    public ScheduleSelectDTO selectSchedule(Long scheduleId) throws SelectScheduleServiceException {
        if(scheduleRepo.findById(scheduleId).isEmpty()) {
            throw new SelectScheduleServiceException("no schedule with id=" + scheduleId);
        }
        ScheduleEntity scheduleEntity = scheduleRepo.selectScheduleById(scheduleId);

        return ScheduleMapper.INSTANCE.getScheduleSelectDTOFromEntity(scheduleEntity);
    }

    private List<ScheduleEntity> getSchedulesByIds(List<Long> ids) {
        List<ScheduleEntity> result = new ArrayList<>();

        for (Long id: ids) {
            result.add(scheduleRepo.selectScheduleById(id));
        }
        return result;
    }

    public List<ScheduleSelectDTO> selectUserSchedules(Long userId) throws SelectScheduleServiceException {
        if (userRepo.findById(userId).isEmpty()) {
            throw new SelectScheduleServiceException("no user with id=" + userId);
        }
        List<Long> selectedSchedules = scheduleRepo.selectUserSchedulesIds(userId);

        return ScheduleMapper.INSTANCE.getSchedulesSelectDTOFromEntities(getSchedulesByIds(selectedSchedules));
    }
}
