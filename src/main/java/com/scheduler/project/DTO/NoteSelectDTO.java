package com.scheduler.project.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NoteSelectDTO {
    private Long id;
    private String note_text;
    private String note_name;
    private Long creation_time;
    private List<String> tags;
    private Long user_id;
}
