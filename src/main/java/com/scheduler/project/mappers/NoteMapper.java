package com.scheduler.project.mappers;

import com.scheduler.project.DTO.NoteDTO;
import com.scheduler.project.DTO.NoteEditDTO;
import com.scheduler.project.entities.NoteEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(target = "user", ignore = true)
    @Mapping(target="id", ignore = true)
    @Mapping(target="creation_time", expression = "java(System.currentTimeMillis())")
    NoteEntity createEntityFromDTO(NoteDTO noteDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target="id", ignore = true)
    @Mapping(target="creation_time", ignore = true)
    void editNote(NoteEditDTO editDTO, @MappingTarget NoteEntity editingNote);
}
