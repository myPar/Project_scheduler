package com.scheduler.project.services.scheduleServices;

import com.scheduler.project.DTO.ResponseScheduleDTO;
import com.scheduler.project.DTO.ScheduleDTO;
import com.scheduler.project.DTO.ScheduleItemDTO;
import com.scheduler.project.DTO.dto_converters.DTOconverter;
import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.entities.ScheduleItemEntity;
import com.scheduler.project.entities.UserEntity;
import com.scheduler.project.repos.ScheduleItemRepo;
import com.scheduler.project.repos.ScheduleRepo;
import com.scheduler.project.repos.UserRepo;
import com.scheduler.project.tools.TimeConverter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class CreateScheduleService {
    @NoArgsConstructor
    public static class CreateScheduleServiceException extends Exception {
        public CreateScheduleServiceException(String message) {
            super(message);
        }
    }
    private final ScheduleItemRepo scheduleItemRepo;
    private final ScheduleRepo scheduleRepo;
    private final UserRepo userRepo;

    @Autowired
    public CreateScheduleService(ScheduleRepo scheduleRepo, UserRepo userRepo, ScheduleItemRepo scheduleItemRepo) {
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
        this.scheduleItemRepo = scheduleItemRepo;
    }

    private List<ScheduleItemEntity> buildScheduleItemEntities(List<ScheduleItemDTO> dtoList, ScheduleEntity schedule) {
        List<ScheduleItemEntity> result = new ArrayList<>();

        for (ScheduleItemDTO dto: dtoList) {
            ScheduleItemEntity entity = ScheduleItemEntity.builder().schedule(schedule)
                                        .count_to_complete(dto.getCount_to_complete())
                                        .difficult(dto.getDifficult()).build();
            result.add(entity);
        }
        return result;
    }

    public ResponseScheduleDTO createSchedule(ScheduleDTO scheduleDTO) throws CreateScheduleServiceException {
        Long user_id = scheduleDTO.getUser_id();
        Optional<UserEntity> userOptional = userRepo.findById(user_id);

        if (userOptional.isEmpty()) {
            throw new CreateScheduleServiceException("no user with id=" + user_id + " found");
        }
        UserEntity user = userOptional.get();

        String start_time_string = scheduleDTO.getStart_time();
        String end_time_string = scheduleDTO.getEnd_time();

        Long start_time;
        Long end_time;

        try {start_time = TimeConverter.getTimeLongValue(start_time_string);}
        catch (DateTimeParseException e) {
            throw new CreateScheduleServiceException("invalid value for start_time attribute - " +
                    start_time_string + ", user pattern - '" + TimeConverter.timeFormat + "'");
        }
        try {end_time = TimeConverter.getTimeLongValue(scheduleDTO.getEnd_time());}
        catch (DateTimeParseException e) {
            throw new CreateScheduleServiceException("invalid value for end_time attribute - " +
                    start_time_string + ", user pattern - '" + TimeConverter.timeFormat + "'");
        }
        if (start_time != null && end_time != null && start_time > end_time) {
            throw new CreateScheduleServiceException("start_time (" + start_time_string + ") should be less than end_time (" +
                    end_time_string + ")");
        }
        List<ScheduleItemDTO> scheduleItemDTOList = scheduleDTO.getScheduleItems();

        // build and save schedule entity
        ScheduleEntity scheduleEntity = ScheduleEntity.builder().user(user)
                                        .schedule_name(scheduleDTO.getSchedule_name())
                                        .start_time(start_time).end_time(end_time)
                                        .build();
        ScheduleEntity savedSchedule = scheduleRepo.save(scheduleEntity);

        // build and save schedule items entities
        List<ScheduleItemEntity> scheduleItemEntities = buildScheduleItemEntities(scheduleItemDTOList, savedSchedule);
        scheduleItemRepo.saveAll(scheduleItemEntities);

        return DTOconverter.getScheduleResponseDTOfromEntity(savedSchedule);
    }
}
