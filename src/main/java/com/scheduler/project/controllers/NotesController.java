package com.scheduler.project.controllers;

import com.scheduler.project.DTO.NoteDTO;
import com.scheduler.project.DTO.NoteEditDTO;
import com.scheduler.project.DTO.NoteSelectDTO;
import com.scheduler.project.services.noteService.AddNoteService;
import com.scheduler.project.services.noteService.AddNoteService.AddNoteServiceException;
import com.scheduler.project.services.noteService.EditNoteService;
import com.scheduler.project.services.noteService.EditNoteService.EditNoteServiceException;
import com.scheduler.project.services.noteService.SelectNotesService;
import com.scheduler.project.services.noteService.SelectNotesService.SelectNotesServiceException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private final AddNoteService addNoteService;
    private final EditNoteService editNoteService;
    private final SelectNotesService selectNotesService;

    public NotesController(AddNoteService addNoteService, EditNoteService editNoteService, SelectNotesService selectNotesService) {
        this.addNoteService = addNoteService;
        this.editNoteService = editNoteService;
        this.selectNotesService = selectNotesService;
    }

    @PostMapping("/addNote")
    public ResponseEntity<?> addNote(@RequestBody NoteDTO noteDTO) {
        try {
            addNoteService.addNote(noteDTO);
            return ResponseEntity.ok().body("note was created");
        }
        catch (AddNoteServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/edit")
    public ResponseEntity<?> editNote(@RequestBody NoteEditDTO noteDTO) {
        try {
            editNoteService.editNote(noteDTO);

            return ResponseEntity.ok().body("note was edited");
        }
        catch (EditNoteServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/addTag")
    public ResponseEntity<?> addTag(@RequestParam @NotNull Long note_id, @RequestParam @NotNull Long tag_id) {
        try {
            NoteSelectDTO result = editNoteService.addTagToNote(note_id, tag_id);
            return ResponseEntity.ok().body(result);
        }
        catch (EditNoteServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getNote")
    public ResponseEntity<?> getNote(@RequestParam @NotNull Long note_id) {
        try {
            NoteSelectDTO result = selectNotesService.getNote(note_id);
            return ResponseEntity.ok().body(result);
        }
        catch (SelectNotesServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getUserNotes")
    public ResponseEntity<?> getUserNotes(@RequestParam @NotNull Long user_id) {
        try {
            List<NoteSelectDTO> result = selectNotesService.getUserNotes(user_id);
            return ResponseEntity.ok().body(result);
        }
        catch (SelectNotesServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
