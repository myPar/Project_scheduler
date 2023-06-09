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
public class GetSchedulesWhichCanBeCompletedService {
    @NoArgsConstructor
    public static class GetSchedulesCompletedServiceException extends Exception {
        public GetSchedulesCompletedServiceException(String message) {
            super(message);
        }
    }
    private final ScheduleRepo scheduleRepo;
    private final UserRepo userRepo;

    @Autowired
    public GetSchedulesWhichCanBeCompletedService(ScheduleRepo scheduleRepo, UserRepo userRepo) {
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
    }

    private List<ScheduleEntity> getSchedulesByIds(List<Long> ids) {
        List<ScheduleEntity> result = new ArrayList<>();

        for (Long id: ids) {
            result.add(scheduleRepo.selectScheduleById(id));
        }
        return result;
    }

    public List<ScheduleSelectDTO> getSchedulesWhichCanBeCompleted(Long userId) throws GetSchedulesCompletedServiceException {
        if (userRepo.findById(userId).isEmpty()) {
            throw new GetSchedulesCompletedServiceException("no user with id=" + userId);
        }
        List<Long> scheduleIds = scheduleRepo.getSchedulesIdsWhichCanBeCompleted(userId);
        List<ScheduleEntity> selectedSchedules = getSchedulesByIds(scheduleIds);

        return ScheduleMapper.INSTANCE.getSchedulesSelectDTOFromEntities(selectedSchedules);
    }
}
