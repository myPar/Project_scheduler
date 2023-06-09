package com.scheduler.project.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteEditDTO {
    @NotNull
    private Long note_id;
    private String note_name;
    private String note_text;
}
