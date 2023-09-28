package com.example.proovitoo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="athlete_id", nullable=false)
    @JsonBackReference
    @ToString.Exclude
    private Athlete athlete;
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;
    @Column(insertable = false, updatable = false)
    private ZonedDateTime timeCreated;
    private Double result;

    public Score(Athlete athlete, Event event) {
        this.athlete = athlete;
        this.event = event;
    }
}
