package com.example.proovitoo.entities;

import com.example.proovitoo.dtos.AthletePostDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.generator.EventType;

import javax.swing.event.DocumentEvent;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Athlete {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    @CreationTimestamp
    private ZonedDateTime timeCreated;
    private String name;
    private Boolean isMale;
    private Integer points;
    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Score> scores = new ArrayList<>();
}
