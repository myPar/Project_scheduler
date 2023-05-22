package com.scheduler.project.DTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@Builder
public class TaskEditDTO {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Nullable
    private String description;

    @NotNull
    private Long task_id;

    @Nullable
    private String task_name;

    @Nullable
    @Min(value = TaskMainDTO.minDifficult, message = "the lowest difficult of the task is 'NO' = 0")
    @Max(value = TaskMainDTO.maxDifficult, message = "the highest difficult of the task is 'HARD' = 3")
    private Integer difficult;

    private String start_time_string;

    @Null(message = "this special field should not be initialized")
    private Long start_time;

    @Nullable
    @Min(value = 0, message = "duration should be a not negative value")
    private Long duration;

    public Long initStartTimeValue() throws DateTimeParseException {
        if (start_time_string == null) {
            start_time = null;

            return null;
        }
        LocalDateTime time = LocalDateTime.parse(start_time_string, formatter);
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());

        start_time = zdt.toInstant().toEpochMilli();

        return start_time;
    }
}
