package com.example.proovitoo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="athlete_id", nullable=false)
    @JsonBackReference
    private Athlete athlete;
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;
    private Timestamp timeCreated;
    private Double result;
    private Integer resultMinutes;
}
