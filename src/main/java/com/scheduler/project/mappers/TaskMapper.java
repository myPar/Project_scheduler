package com.scheduler.project.mappers;

import com.scheduler.project.DTO.TaskEditDTO;
import com.scheduler.project.entities.CompletedTaskDataEntity;
import com.scheduler.project.entities.TaskEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    // null - don't modify
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "overdue", ignore = true)
    void updateTaskFromDTO(TaskEditDTO taskEditDTO, @MappingTarget TaskEntity task);

    @Mapping(target = "completion_time", expression = "java(System.currentTimeMillis())")
    @Mapping(target = "user", expression = "java(completedTask.getUser())")
    @Mapping(target = "schedule_completion", ignore = true)
    CompletedTaskDataEntity getCompletionDataFromTask(TaskEntity completedTask);
}
