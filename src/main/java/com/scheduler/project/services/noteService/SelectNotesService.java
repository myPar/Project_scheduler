package com.scheduler.project.services.noteService;

import com.scheduler.project.DTO.NoteSelectDTO;
import com.scheduler.project.entities.NoteEntity;
import com.scheduler.project.entities.NotesTagsEntity;
import com.scheduler.project.entities.ScheduleEntity;
import com.scheduler.project.mappers.NoteMapper;
import com.scheduler.project.repos.NotesRepo;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SelectNotesService {
    @NoArgsConstructor
    public static class SelectNotesServiceException extends Exception {
        public SelectNotesServiceException(String message) {
            super(message);
        }
    }
    private final NotesRepo notesRepo;
    private final UserRepo userRepo;

    @Autowired
    public SelectNotesService(NotesRepo notesRepo, UserRepo userRepo) {
        this.notesRepo = notesRepo;
        this.userRepo = userRepo;
    }

    private List<NoteEntity> getNotesByIds(List<Long> ids) {
        List<NoteEntity> result = new ArrayList<>();

        for (Long id: ids) {
            result.add(notesRepo.selectNoteById(id).get());
        }
        return result;
    }
    public NoteSelectDTO getNote(Long note_id) throws SelectNotesServiceException {
        Optional<NoteEntity> noteOptional = notesRepo.selectNoteById(note_id);
        if (noteOptional.isEmpty()) {
            throw new SelectNotesServiceException("no note with id=" + note_id);
        }
        return NoteMapper.INSTANCE.getDTOFromEntity(noteOptional.get());
    }

    public List<NoteSelectDTO> getUserNotes(Long user_id) throws SelectNotesServiceException {
        if (userRepo.findById(user_id).isEmpty()) {
            throw new SelectNotesServiceException("not user with id=" + user_id);
        }
        List<NoteEntity> selectedNotes = getNotesByIds(notesRepo.selectUserNotesIds(user_id));
        return NoteMapper.INSTANCE.getSelectDTOsFromEntities(selectedNotes);
    }
}
