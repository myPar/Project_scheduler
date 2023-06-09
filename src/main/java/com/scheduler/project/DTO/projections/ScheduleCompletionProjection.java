package com.scheduler.project.DTO.projections;

public interface ScheduleCompletionProjection {
    Integer getCount_to_complete();
    Integer getTotal_completed();
    Integer getDifficult();
    Long getSchedule_id();
    String getSchedule_name();
}
