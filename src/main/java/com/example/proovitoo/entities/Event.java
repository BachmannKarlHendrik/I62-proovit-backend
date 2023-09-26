package com.example.proovitoo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    private String name;
    private String nameEst;
    private Double aCoefficient;
    private Double bCoefficient;
    private Double cCoefficient;
    private Boolean hasMinutes;
    private String unit;
    private Boolean isWomen;
    private Boolean isMen;
}
