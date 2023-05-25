package com.scheduler.project.mappers;

import com.scheduler.project.DTO.EditScheduleDTO;
import com.scheduler.project.DTO.ResponseScheduleDTO;
import com.scheduler.project.DTO.ScheduleItemDTO;
import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.entities.ScheduleItemEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

@Mapper
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "scheduleItems", ignore = true)
    @Mapping(target = "user_id", expression = "java(scheduleEntity.getUser().getId())")
    @Mapping(target = "start_time", expression = "java(com.scheduler.project.tools.TimeConverter.getTimeStringValue(scheduleEntity.getStart_time()))")
    @Mapping(target = "end_time", expression = "java(com.scheduler.project.tools.TimeConverter.getTimeStringValue(scheduleEntity.getEnd_time()))")
    ResponseScheduleDTO getResponseScheduleDTO(ScheduleEntity scheduleEntity) throws DateTimeException;

    ScheduleItemDTO getScheduleItemDTO(ScheduleItemEntity scheduleItemEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    void updateScheduleItemFromDTO(ScheduleItemDTO scheduleItemDTO, @MappingTarget ScheduleItemEntity scheduleItemEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "start_time", expression = "java(com.scheduler.project.tools.TimeConverter.getTimeLongValue(scheduleDTO.getStart_time()))")
    @Mapping(target = "end_time", expression = "java(com.scheduler.project.tools.TimeConverter.getTimeLongValue(scheduleDTO.getEnd_time()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scheduleItems", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "overdue", ignore = true)
    void updateScheduleFromDTO(EditScheduleDTO scheduleDTO, @MappingTarget ScheduleEntity scheduleEntity) throws DateTimeParseException;
}
