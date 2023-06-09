package com.scheduler.project.mappers;

import com.scheduler.project.DTO.UserDTO;
import com.scheduler.project.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANSE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    UserEntity mapEntityFromDTO(UserDTO userDTO);
}
