package com.example.proovitoo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
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
    private UUID id;
    private Timestamp timeCreated;
    private String name;
    private Boolean isMale;
    private Integer points;
    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Score> scores;
}
