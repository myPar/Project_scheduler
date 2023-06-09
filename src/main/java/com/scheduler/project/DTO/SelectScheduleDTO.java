package com.scheduler.project.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectScheduleDTO {
    @NotNull(message = "user id should not be a null value")
    private Long user_id;
    private String start_time;
    private String end_time;
}
