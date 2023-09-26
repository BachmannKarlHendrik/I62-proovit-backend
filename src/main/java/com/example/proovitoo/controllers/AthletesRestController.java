package com.example.proovitoo.controllers;

import com.example.proovitoo.dtos.AthletePostDto;
import com.example.proovitoo.entities.Athlete;
import com.example.proovitoo.entities.Score;
import com.example.proovitoo.repositories.AthletesRepository;
import com.example.proovitoo.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AthletesRestController {
    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private AthletesRepository athletesRepository;

    @GetMapping("athletes")
    public ResponseEntity<?> getAllAthletes() {
        return ResponseEntity.ok(athletesRepository.findAllByOrderByTimeCreatedDesc());
    }

    @GetMapping("athlete/{id}")
    public ResponseEntity<?> getAthleteById(@PathVariable("id") String id) {
        UUID uuid = null;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("UUID " + id + " is not formatted correctly.", HttpStatus.BAD_REQUEST);
        }
        Optional<Athlete> athlete = athletesRepository.findById(uuid);
        if(athlete.isEmpty()) {
            return new ResponseEntity<>("Athlete " + id + " does not exist.",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(athlete.get());
    }

    @PostMapping("athlete")
    public ResponseEntity<?> postAthlete(@RequestBody AthletePostDto athleteDto) {
        if(athleteDto.hasNullValues()) {
            return new ResponseEntity<>("Entered data has null values.", HttpStatus.BAD_REQUEST);
        }
        Athlete athlete = athleteDto.createAthlete();

        if(athlete.getIsMale()) {
            eventsRepository.findByIsMenTrue().forEach(event -> athlete.getScores().add(new Score(athlete,event)));
        }
        else {
            eventsRepository.findByIsWomenTrue().forEach(event -> athlete.getScores().add(new Score(athlete,event)));
        }

        athletesRepository.saveAndFlush(athlete);

        return ResponseEntity.ok(athlete);
    }
}
