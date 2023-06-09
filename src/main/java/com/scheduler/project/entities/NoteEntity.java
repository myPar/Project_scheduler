package com.scheduler.project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name="notes")
public class NoteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @OneToMany(mappedBy="note")
    private List<NotesTagsEntity> tags;

    @NotBlank
    private String note_text;

    @NotBlank
    private String note_name;

    @NotNull
    private Long creation_time;
}
