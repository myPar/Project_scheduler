package com.scheduler.project.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScheduleDTO {
    @NotNull(message = "user id should not be a null value")
    private Long user_id;
    private String start_time;
    private String end_time;

    @NotBlank(message = "schedule name should contains characters")
    private String schedule_name;

    @NotEmpty(message = "schedule should contains items")
    List<ScheduleItemDTO> scheduleItems;
}
