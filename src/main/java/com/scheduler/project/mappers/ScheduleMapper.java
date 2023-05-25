package com.scheduler.project.mappers;

import com.scheduler.project.DTO.ResponseScheduleDTO;
import com.scheduler.project.DTO.ScheduleItemDTO;
import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.entities.ScheduleItemEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.time.DateTimeException;

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
}
