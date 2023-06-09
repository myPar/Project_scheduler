package com.scheduler.project.services.scheduleServices;

import com.scheduler.project.DTO.projections.ScheduleCompletionProjection;
import com.scheduler.project.repos.ScheduleRepo;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSchedulesCompletionDataService {
    private ScheduleRepo scheduleRepo;
    private UserRepo userRepo;

    @NoArgsConstructor
    public static class GetSchedulesCompletionDataServiceException extends Exception {
        public GetSchedulesCompletionDataServiceException(String message) {
            super(message);
        }
    }
    @Autowired
    public GetSchedulesCompletionDataService(ScheduleRepo scheduleRepo, UserRepo userRepo) {
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
    }
    public List<ScheduleCompletionProjection> getSchedulesCompletionData(Long userId) throws GetSchedulesCompletionDataServiceException {
        if (userRepo.findById(userId).isEmpty()) {
            throw new GetSchedulesCompletionDataServiceException("no user with id=" + userId);
        }
        return scheduleRepo.getSchedulesCompletionPossibilityData(userId);
    }
}
