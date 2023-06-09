package com.scheduler.project.repos;

import com.scheduler.project.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotesRepo extends JpaRepository<NoteEntity, Long> {
    @Query(value = "select * from notes left outer join \n" +
            "(select note_id, tag_name from notes_tags inner join (select * from tags) tt \n" +
            "on tt.id = notes_tags.tag_id) t \n" +
            "on notes.id=t.note_id where notes.id= :noteId", nativeQuery = true)
    Optional<NoteEntity> selectNoteById(@Param(value = "noteId") Long noteId);

    @Query(value = "select id from notes where notes.user_id= :userId", nativeQuery = true)
    List<Long> selectUserNotesIds(@Param(value="userId") Long userId);
}
