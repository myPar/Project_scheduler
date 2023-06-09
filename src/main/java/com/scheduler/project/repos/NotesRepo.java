package com.scheduler.project.repos;

import com.scheduler.project.entities.NoteEntity;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepo extends CrudRepository<NoteEntity, Long> {
}
