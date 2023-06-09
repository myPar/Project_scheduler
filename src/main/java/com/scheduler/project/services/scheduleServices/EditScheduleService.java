package com.scheduler.project.services.scheduleServices;

import com.scheduler.project.DTO.ScheduleItemDTO;
import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.entities.ScheduleItemEntity;
import com.scheduler.project.mappers.ScheduleMapper;
import com.scheduler.project.repos.ScheduleItemRepo;
import com.scheduler.project.repos.ScheduleRepo;
import com.scheduler.project.tools.TimeConverter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.scheduler.project.DTO.EditScheduleDTO;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;

@Service
public class EditScheduleService {
    @NoArgsConstructor
    public static class EditScheduleServiceException extends Exception {
        public EditScheduleServiceException(String message) {
            super(message);
        }
    }
    private final ScheduleRepo scheduleRepo;
    private final ScheduleItemRepo scheduleItemRepo;

    @Autowired
    public EditScheduleService(ScheduleRepo scheduleRepo, ScheduleItemRepo scheduleItemRepo) {
        this.scheduleRepo = scheduleRepo;
        this.scheduleItemRepo = scheduleItemRepo;
    }

    private boolean oneIsNull(Long start_time, Long end_time) {
        return (start_time == null && end_time != null) || (start_time != null && end_time == null);
    }
    private boolean bothAreNotNull(Long start_time, Long end_time) {
        return start_time != null && end_time != null;
    }

    private void validateTimeInterval(Long start_time, Long end_time) throws EditScheduleServiceException {
        if (oneIsNull(start_time, end_time)) {
            throw new EditScheduleServiceException("start_time and end_time BOTH should contain ore should not contain a value");
        }
        if (bothAreNotNull(start_time, end_time)) {
            if (end_time < start_time) {                // TODO maybe add getting string values of time interval from entity
                throw new EditScheduleServiceException("start_time should be less than end_time");
            }
        }
    }

    private void editAndSaveScheduleItems(List<ScheduleItemDTO> scheduleItemDTOS, ScheduleEntity schedule) throws EditScheduleServiceException {
        for (ScheduleItemDTO scheduleItemDTO: scheduleItemDTOS) {
            Integer difficult = scheduleItemDTO.getDifficult();
            Long scheduleId = schedule.getId();

            List<ScheduleItemEntity> selectedScheduleItems = scheduleItemRepo.selectScheduleItemsByDifficult(difficult, scheduleId);
            if (selectedScheduleItems.size() > 1) {
                throw new EditScheduleServiceException("FATAL: inconsistent state of the database - " +
                        " schedule item with specified difficult can exists only in one copy");
            }
            if (selectedScheduleItems.isEmpty()) {
                // no such item, so build new and save
                ScheduleItemEntity scheduleItemEntity = ScheduleItemEntity.builder().schedule(schedule)
                                                        .count_to_complete(scheduleItemDTO.getCount_to_complete())
                                                        .difficult(scheduleItemDTO.getDifficult()).build();
                scheduleItemRepo.save(scheduleItemEntity);
            }
            else {
                // modify existing item
                ScheduleItemEntity modifyingScheduleItem = selectedScheduleItems.get(0);
                ScheduleMapper mapper = ScheduleMapper.INSTANCE;
                mapper.updateScheduleItemFromDTO(scheduleItemDTO, modifyingScheduleItem);

                scheduleItemRepo.save(modifyingScheduleItem);
            }
        }
    }

    public void editSchedule(EditScheduleDTO editScheduleDTO) throws EditScheduleServiceException {
        Long scheduleId = editScheduleDTO.getId();
        Optional<ScheduleEntity> scheduleOptionalEntity = scheduleRepo.findById(scheduleId);

        if (scheduleOptionalEntity.isEmpty()) {
            throw new EditScheduleServiceException("no schedule with id=" + scheduleId + " exists");
        }
        ScheduleEntity editingScheduleEntity = scheduleOptionalEntity.get();
        // modify entity
        ScheduleMapper mapper = ScheduleMapper.INSTANCE;
        try {
            mapper.updateScheduleFromDTO(editScheduleDTO, editingScheduleEntity);
        }
        catch (DateTimeException e) {
            throw new EditScheduleServiceException("invalid time value, use the format - '" + TimeConverter.timeFormat + "'");
        }
        // validate entity
        Long start_time = editingScheduleEntity.getStart_time();
        Long end_time = editingScheduleEntity.getEnd_time();

        validateTimeInterval(start_time, end_time);

        if (editingScheduleEntity.getSchedule_name().isBlank()) {
            throw new EditScheduleServiceException("schedule name should contains characters");
        }
        editAndSaveScheduleItems(editScheduleDTO.getScheduleItems(), editingScheduleEntity);

        if (editingScheduleEntity.checkOverdue()) {
            editingScheduleEntity.setOverdue(true);
        }
        else if (editingScheduleEntity.getOverdue()) {
            editingScheduleEntity.setOverdue(false);    // set overdue back to false if now is not overdue
        }
        scheduleRepo.save(editingScheduleEntity);
    }
}
