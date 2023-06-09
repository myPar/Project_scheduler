package com.scheduler.project.services.noteService;

import com.scheduler.project.DTO.NoteEditDTO;
import com.scheduler.project.entities.NoteEntity;
import com.scheduler.project.mappers.NoteMapper;
import com.scheduler.project.repos.NotesRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditNoteService {
    private final NotesRepo notesRepo;

    @NoArgsConstructor
    public static class EditNoteServiceException extends Exception {
        public EditNoteServiceException(String message) {
            super(message);
        }
    }

    @Autowired
    public EditNoteService(NotesRepo notesRepo) {
        this.notesRepo = notesRepo;
    }

    private boolean checkBlank(String checkString) {
        if (checkString != null) {
            return checkString.isBlank();
        }
        return false;
    }

    public NoteEntity editNote(NoteEditDTO noteDTO) throws EditNoteServiceException{
        Optional<NoteEntity> optionalNote = notesRepo.findById(noteDTO.getNote_id());
        if (optionalNote.isEmpty()) {
            throw new EditNoteServiceException("no note with id=" + noteDTO.getNote_id() + " found");
        }
        NoteEntity editingNote = optionalNote.get();
        if (checkBlank(editingNote.getNote_name())) {
            throw new EditNoteServiceException("note name should not be blank");
        }
        if (checkBlank(editingNote.getNote_text())) {
            throw new EditNoteServiceException("note text should not be blank");
        }
        NoteMapper.INSTANCE.editNote(noteDTO, editingNote);

        return notesRepo.save(editingNote);
    }
}
