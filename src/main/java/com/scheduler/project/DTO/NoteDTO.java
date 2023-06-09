package com.scheduler.project.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteDTO {
    @NotBlank
    private String note_text;

    @NotBlank
    private String note_name;

    @NotNull
    private Long user_id;
}
