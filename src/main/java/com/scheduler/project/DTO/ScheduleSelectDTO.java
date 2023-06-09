package com.scheduler.project.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScheduleSelectDTO {
    private Long id;

    private Long user_id;

    private Long start_time;

    private Long end_time;

    private String schedule_name;

    private Boolean completed;

    private Boolean overdue;

    private List<ScheduleItemDTO> scheduleItems;
}
