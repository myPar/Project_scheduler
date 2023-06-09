package com.scheduler.project.services.noteService;

import com.scheduler.project.DTO.NoteDTO;
import com.scheduler.project.entities.NoteEntity;
import com.scheduler.project.entities.UserEntity;
import com.scheduler.project.mappers.NoteMapper;
import com.scheduler.project.repos.NotesRepo;
import com.scheduler.project.repos.UserRepo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddNoteService {
    @NoArgsConstructor
    public static class AddNoteServiceException extends Exception {
        public AddNoteServiceException(String message) {
            super(message);
        }
    }
    private final UserRepo userRepo;
    private final NotesRepo notesRepo;

    public AddNoteService(NotesRepo notesRepo, UserRepo userRepo) {
        this.notesRepo = notesRepo;
        this.userRepo = userRepo;
    }

    public NoteEntity addNote(NoteDTO note) throws AddNoteServiceException {
        Optional<UserEntity> ownerOptional = userRepo.findById(note.getUser_id());
        if (ownerOptional.isEmpty()) {
            throw new AddNoteServiceException("user with id=" + note.getUser_id() + " doesn't exists");
        }
        UserEntity owner = ownerOptional.get();
        NoteEntity newNote = NoteMapper.INSTANCE.createEntityFromDTO(note);
        newNote.setUser(owner);

        return notesRepo.save(newNote);
    }
}
