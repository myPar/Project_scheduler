package com.scheduler.project.repos;

import com.scheduler.project.entities.NotesTagsEntity;
import com.scheduler.project.entities.TasksTagsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface NotesTagsRepo extends JpaRepository<NotesTagsEntity, Long> {
    @Transactional
    @Modifying
    @Query(value="INSERT INTO notes_tags (note_id, tag_id) VALUES(:note_id, :tag_id)", nativeQuery = true)
    void insertItem(@Param(value = "tag_id") Long tag_id, @Param(value = "note_id") Long note_id);

    @Query(value="SELECT * FROM notes_tags t WHERE t.tag_id=:tag_id AND t.note_id =:note_id", nativeQuery = true)
    Collection<NotesTagsEntity> checkNoteHasTag(@Param(value="note_id") Long note_id, @Param(value = "tag_id") Long tag_id);
}
