package com.scheduler.project.controllers;

import com.scheduler.project.DTO.NoteDTO;
import com.scheduler.project.DTO.NoteEditDTO;
import com.scheduler.project.services.noteService.AddNoteService;
import com.scheduler.project.services.noteService.AddNoteService.AddNoteServiceException;
import com.scheduler.project.services.noteService.EditNoteService;
import com.scheduler.project.services.noteService.EditNoteService.EditNoteServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notes")
public class NotesController {
    private final AddNoteService addNoteService;
    private final EditNoteService editNoteService;

    public NotesController(AddNoteService addNoteService, EditNoteService editNoteService) {
        this.addNoteService = addNoteService;
        this.editNoteService = editNoteService;
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
    @PutMapping("edit")
    public ResponseEntity<?> editNote(@RequestBody NoteEditDTO noteDTO) {
        try {
            editNoteService.editNote(noteDTO);

            return ResponseEntity.ok().body("note was edited");
        }
        catch (EditNoteServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
