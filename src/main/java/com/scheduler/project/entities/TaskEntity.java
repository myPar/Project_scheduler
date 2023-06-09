package com.scheduler.project.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tasks")
public class TaskEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String description;

    private String task_name;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "task")
    List<TasksTagsEntity> tags;

    private Integer difficult;

    private Long start_time;

    @Builder.Default
    private Boolean completed = false;

    @Builder.Default
    private Boolean overdue = false;

    private Long duration;  // in minutes

    public boolean checkOverdue() {
        if (start_time == null || duration == null) {return false;}
        long end_time = start_time + TimeUnit.MINUTES.toMillis(duration);

        return !completed && end_time < System.currentTimeMillis();
    }
}
