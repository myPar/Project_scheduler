package com.scheduler.project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="schedules")
public class ScheduleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    private Long start_time;

    private Long end_time;

    private String schedule_name;

    @Builder.Default    // this value inits with builder only!
    private Boolean completed = false;
    @Builder.Default
    private Boolean overdue = false;

    @JsonIgnore
    @OneToMany(mappedBy = "schedule")
    private List<ScheduleItemEntity> scheduleItems;

    public boolean isOverdue() {
        if (end_time == null) {return false;}

        return !completed && end_time < System.currentTimeMillis();
    }
}
