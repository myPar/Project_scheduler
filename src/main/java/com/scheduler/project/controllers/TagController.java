package com.scheduler.project.controllers;

import com.scheduler.project.DTO.TagDTO;
import com.scheduler.project.services.tagServices.CreateTagService;
import com.scheduler.project.services.tagServices.CreateTagService.CreateTagServiceException;
import com.scheduler.project.services.tagServices.DeleteTagService;
import com.scheduler.project.services.tagServices.DeleteTagService.DeleteTagServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final CreateTagService createTagService;
    private final DeleteTagService deleteTagService;

    @Autowired
    public TagController(CreateTagService createTagService, DeleteTagService deleteTagService) {
        this.createTagService = createTagService;
        this.deleteTagService = deleteTagService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createTag(@RequestBody @Valid TagDTO tagDTO) {
        try {
            createTagService.createTag(tagDTO);

            return ResponseEntity.ok().body("tag successfully created");
        }
        catch (CreateTagServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/remove")
    public ResponseEntity<?> deleteTag(@RequestParam(name="id") @NotNull Long tagId) {
        try {
            deleteTagService.deleteTag(tagId);
            return ResponseEntity.ok().body("tag with id=" + tagId + " was successfully deleted");
        }
        catch (DeleteTagServiceException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
