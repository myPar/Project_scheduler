package com.scheduler.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="completed_task_data")
public class CompletedTaskDataEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    private Long completion_time;

    private Long duration;
    private Integer difficult;

    @Builder.Default
    private Boolean schedule_completion = false;
}
