package com.scheduler.project.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskSelectDTO {
    private Long id;
    private String description;

    private String task_name;

    private Long user_id;

    List<String> tags;

    private Integer difficult;

    private Long start_time;

    private Boolean completed;

    private Boolean overdue;

    private Long duration;
}
