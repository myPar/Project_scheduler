package com.scheduler.project.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EditScheduleDTO {
    @NotNull(message = "schedule id should be not null")
    private Long id;

    @Nullable
    private String start_time;

    @Nullable
    private String end_time;

    @Nullable
    private String schedule_name;

    @Valid
    @Nullable
    List<ScheduleItemDTO> scheduleItems;
}
