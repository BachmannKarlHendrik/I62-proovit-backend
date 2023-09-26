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

    public void recalculatePoints() {
        points = 0;
        scores.forEach(score -> {
            //Nullidega tähistatakse skoorid, mis on veel mõõtmata.
            if(score.getResult() == null) {
                return;
            }
            //Kõik tulemused ümardatakse alla (int) castimisel. Allikas: https://en.wikipedia.org/wiki/Decathlon
            if (score.getEvent().getUnit().equalsIgnoreCase("m")) {
                points += (int) (score.getEvent().getACoefficient() * Math.pow((score.getResult() - score.getEvent().getBCoefficient()), score.getEvent().getCCoefficient()));
            } else if (score.getEvent().getUnit().equalsIgnoreCase("s")) {
                points += (int) (score.getEvent().getACoefficient() * Math.pow((score.getEvent().getBCoefficient() - score.getResult()), score.getEvent().getCCoefficient()));
            }
        });
    }
}
