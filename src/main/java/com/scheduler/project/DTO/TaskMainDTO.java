package com.scheduler.project.DTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskMainDTO {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final int minDifficult = 0;
    public static final int maxDifficult = 3;
    public static final String defaultStringValue = "";
    public static final String defaultStartTime = "1970-01-01 00:00";
    public static final Long defaultDuration = 0L;
    public static final Integer defaultDifficult = 0;

    @Nullable
    private String description;

    @NotBlank(message = "task name should contains characters")
    private String task_name;

    @NotNull(message = "user id should not be a null value")
    private Long user_id;

    @Nullable
    @Min(value = minDifficult, message = "the lowest difficult of the task is 'NO' = 0")
    @Max(value = maxDifficult, message = "the highest difficult of the task is 'HARD' = 3")
    private Integer difficult;

    private String start_time;

    @Nullable
    @Min(value = 0, message = "duration should be a not negative value")
    private Long duration;

    public Long getStartTimeValue() throws DateTimeParseException {
        if (start_time == null) {
            return null;
        }
        LocalDateTime time = LocalDateTime.parse(start_time, formatter);
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());

        return zdt.toInstant().toEpochMilli();
    }
}
