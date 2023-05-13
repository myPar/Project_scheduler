package com.scheduler.project.DTO;
import jakarta.validation.constraints.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskRequestDTO {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final int minDifficult = 0;
    private static final int maxDifficult = 3;
    private String description;

    @NotBlank(message = "task name should contains characters")
    private String task_name;

    @NotNull(message = "user id should not be a null value")
    private Long user_id;

    @Min(value = minDifficult, message = "the lowest difficult of the task is 'NO' = 0")
    @Max(value = maxDifficult, message = "the highest difficult of the task is 'HARD' = 3")
    private Integer difficult;

    private String start_time;
    @Min(value = 0, message = "duration should be a positive value")
    private Long duration;

    // define conversation methods
    public Date getStartTimeDate(String timezone) throws ParseException {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.parse(this.start_time);
    }

    public void setStartTimeDate(Date date, String timezone) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        this.start_time = dateFormat.format(date);
    }
}
