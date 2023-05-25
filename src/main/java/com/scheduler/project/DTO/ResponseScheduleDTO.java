package com.scheduler.project.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseScheduleDTO {
    private Long id;
    private Long user_id;
    private String start_time;
    private String end_time;

    private String schedule_name;
    private Boolean completed;
    private Boolean overdue;

    List<ScheduleItemDTO> scheduleItems;
}
