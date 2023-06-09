package com.scheduler.project.services.noteService;

import com.scheduler.project.DTO.NoteEditDTO;
import com.scheduler.project.DTO.NoteSelectDTO;
import com.scheduler.project.entities.NoteEntity;
import com.scheduler.project.entities.TagEntity;
import com.scheduler.project.mappers.NoteMapper;
import com.scheduler.project.repos.NotesRepo;
import com.scheduler.project.repos.NotesTagsRepo;
import com.scheduler.project.repos.TagRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditNoteService {
    private final NotesRepo notesRepo;
    private final TagRepo tagRepo;
    private final NotesTagsRepo notesTagsRepo;

    @NoArgsConstructor
    public static class EditNoteServiceException extends Exception {
        public EditNoteServiceException(String message) {
            super(message);
        }
    }

    @Autowired
    public EditNoteService(NotesRepo notesRepo, TagRepo tagRepo, NotesTagsRepo notesTagsRepo) {
        this.notesRepo = notesRepo;
        this.tagRepo = tagRepo;
        this.notesTagsRepo = notesTagsRepo;
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

    public NoteSelectDTO addTagToNote(Long note_id, Long tag_id) throws EditNoteServiceException {
        Optional<TagEntity> tagOptional = tagRepo.findById(tag_id);
        if (tagOptional.isEmpty()) {
            throw new EditNoteServiceException("not tag with id=" + tag_id);
        }
        Optional<NoteEntity> noteOptional = notesRepo.findById(note_id);
        if (noteOptional.isEmpty()) {
            throw new EditNoteServiceException("no note with id=" + note_id);
        }
        if (notesTagsRepo.checkNoteHasTag(note_id, tag_id).size() > 0) {
            throw new EditNoteServiceException("note with id=" + note_id + " already contains tag with id=" + tag_id);
        }
        notesTagsRepo.insertItem(tag_id, note_id);

        Optional<NoteEntity> editedNote = notesRepo.selectNoteById(note_id);
        if (editedNote.isEmpty()) {
            throw new EditNoteServiceException("FATA: edited note id=" + note_id + " was not found");
        }
        return NoteMapper.INSTANCE.getDTOFromEntity(editedNote.get());
    }
}
