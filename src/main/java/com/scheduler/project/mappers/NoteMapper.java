package com.scheduler.project.mappers;

import com.scheduler.project.DTO.NoteDTO;
import com.scheduler.project.DTO.NoteEditDTO;
import com.scheduler.project.DTO.NoteSelectDTO;
import com.scheduler.project.entities.NoteEntity;
import com.scheduler.project.entities.NotesTagsEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NoteMapper {
    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(target = "user", ignore = true)
    @Mapping(target="id", ignore = true)
    @Mapping(target="creation_time", expression = "java(System.currentTimeMillis())")
    @Mapping(target = "tags", ignore = true)
    NoteEntity createEntityFromDTO(NoteDTO noteDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target="id", ignore = true)
    @Mapping(target="creation_time", ignore = true)
    @Mapping(target = "tags", ignore = true)
    void editNote(NoteEditDTO editDTO, @MappingTarget NoteEntity editingNote);

    List<NoteSelectDTO> getSelectDTOsFromEntities(List<NoteEntity> noteEntities);

    @Mapping(target = "user_id", expression = "java(noteEntity.getUser().getId())")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "extractNames")
    NoteSelectDTO getDTOFromEntity(NoteEntity noteEntity);

    @Named(value = "extractNames")
    List<String> extractNames(List<NotesTagsEntity> tagEntities);

    default String extractTagName(NotesTagsEntity notesTagsEntity) {
        return notesTagsEntity.getTag().getTag_name();
    }
}
