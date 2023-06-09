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
public class SelectMostDifficultScheduleService {
    @NoArgsConstructor
    public static class SelectMostDifficultScheduleServiceException extends Exception {
        public SelectMostDifficultScheduleServiceException(String message) {
            super(message);
        }
    }
    private final UserRepo userRepo;
    private final ScheduleRepo scheduleRepo;

    @Autowired
    public SelectMostDifficultScheduleService(ScheduleRepo scheduleRepo, UserRepo userRepo) {
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

    public List<ScheduleSelectDTO> selectMostDifficultSchedules(Long userId) throws SelectMostDifficultScheduleServiceException {
        if (userRepo.findById(userId).isEmpty()) {
            throw new SelectMostDifficultScheduleServiceException("no user with id=" + userId + " found");
        }
        List<Long> scheduleIds = scheduleRepo.selectMostDifficultSchedules(userId);
        List<ScheduleEntity> selectedSchedules = getSchedulesByIds(scheduleIds);

        return ScheduleMapper.INSTANCE.getSchedulesSelectDTOFromEntities(selectedSchedules);
    }
}
