package com.example.proovitoo.entities;

import com.example.proovitoo.dtos.AthletePostDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.generator.EventType;
import org.springframework.core.annotation.Order;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @OrderBy("event_id")
    private List<Score> scores = new ArrayList<>();

    public void recalculatePoints() {
        points = 0;
        scores.forEach(score -> {
            //Nullidega tähistatakse skoorid, mis on veel mõõtmata.
            if(score.getResult() == null) {
                return;
            }
            //Kõik tulemused ümardatakse alla (int) castimisel. Allikas: https://en.wikipedia.org/wiki/Decathlon
            if (score.getEvent().getUnit().equalsIgnoreCase("m") || score.getEvent().getUnit().equalsIgnoreCase("cm")) {
                //Kui tulemus on väiksem kui B koefitsient, tagastab Math.pow NaN ning punktidele ei liideta mitte miskit ning võistleja saab soorituse eest 0 punkti.
                points += (int) (score.getEvent().getACoefficient() * Math.pow((score.getResult() - score.getEvent().getBCoefficient()), score.getEvent().getCCoefficient()));
            } else if (score.getEvent().getUnit().equalsIgnoreCase("s")) {
                //Kui tulemus on suurem kui B koefitsient, tagastab Math.pow Nan ning punktidele ei liideta mitte miskit ning võitleja saab soorituse eest 0 punkti.
                points += (int) (score.getEvent().getACoefficient() * Math.pow((score.getEvent().getBCoefficient() - score.getResult()), score.getEvent().getCCoefficient()));
            }
        });
    }
}
