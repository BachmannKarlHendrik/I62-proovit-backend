package com.example.proovitoo.dtos;

import com.example.proovitoo.entities.Athlete;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AthletePostDto {
    private String name;
    private Boolean isMale;

    public boolean hasNullValues() {
        if(name == null || isMale == null) {
            return true;
        }
        return false;
    }

    public Athlete createAthlete() {
        if(hasNullValues()) {
            throw new RuntimeException(this + " contains null values.");
        }
        Athlete athlete = new Athlete();
        athlete.setName(name);
        athlete.setIsMale(isMale);
        return athlete;
    }
}
