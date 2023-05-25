package com.scheduler.project.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ScheduleItemDTO {
    public static final int minDifficult = 0;
    public static final int maxDifficult = 3;
    public static final int minCompleteCount = 0;

    @Min(value = minDifficult, message = "the lowest difficult of the task is 'NO' = 0")
    @Max(value = maxDifficult, message = "the highest difficult of the task is 'HIGH' = 3")
    @NotNull
    private Integer difficult;

    @Min(value = minCompleteCount, message = "min complete count should be " + minCompleteCount)
    @NotNull
    private Integer count_to_complete;
}
