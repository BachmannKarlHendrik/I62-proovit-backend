package com.example.proovitoo.controllers;

import com.example.proovitoo.dtos.AthletePageDto;
import com.example.proovitoo.dtos.AthletePostDto;
import com.example.proovitoo.entities.Athlete;
import com.example.proovitoo.entities.Score;
import com.example.proovitoo.repositories.AthletesRepository;
import com.example.proovitoo.repositories.EventsRepository;
import com.example.proovitoo.repositories.ScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class AthletesRestController {
    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private AthletesRepository athletesRepository;

    @Autowired
    private ScoresRepository scoresRepository;

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
        if (athlete.isEmpty()) {
            return new ResponseEntity<>("Athlete " + id + " does not exist.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(athlete.get());
    }

    @PostMapping("athlete")
    public ResponseEntity<?> postAthlete(@RequestBody AthletePostDto athleteDto) {
        if (athleteDto.hasNullValues()) {
            return new ResponseEntity<>("Entered data has null values.", HttpStatus.BAD_REQUEST);
        }
        Athlete athlete = athleteDto.createAthlete();

        if (athlete.getIsMale()) {
            eventsRepository.findByIsMenTrue().forEach(event -> athlete.getScores().add(new Score(athlete, event)));
        } else {
            eventsRepository.findByIsWomenTrue().forEach(event -> athlete.getScores().add(new Score(athlete, event)));
        }
        athlete.setPoints(0);
        athletesRepository.saveAndFlush(athlete);

        return ResponseEntity.ok(athlete);
    }

    @GetMapping("athletes/latest")
    public ResponseEntity<?> latestAthletes(@RequestParam("page") Integer pageNr) {
        if (pageNr <= 0) {
            return new ResponseEntity<>("Pagination starts at 1", HttpStatus.BAD_REQUEST);
        }
        Page<Athlete> page = athletesRepository.findAllByOrderByTimeCreatedDesc(PageRequest.of(pageNr - 1, 10));
        return ResponseEntity.ok(AthletePageDto.createFromPage(page));
    }

    @GetMapping("athletes/inProgress")
    public ResponseEntity<?> inProgressAthletes(@RequestParam("page") Integer pageNr) {
        if (pageNr <= 0) {
            return new ResponseEntity<>("Pagination starts at 1", HttpStatus.BAD_REQUEST);
        }
        List<UUID> athleteIds = scoresRepository.findAllByResultIsNull().stream().map(score -> score.getAthlete().getId())
                .distinct().collect(Collectors.toList());
        Page<Athlete> page = athletesRepository.findAllByIdInOrderByTimeCreatedDesc(athleteIds,
                PageRequest.of(pageNr - 1, 10));
        return ResponseEntity.ok(AthletePageDto.createFromPage(page));
    }

    @GetMapping("athletes/top3Women")
    public ResponseEntity<?> top3Women() {
        return ResponseEntity.ok(athletesRepository.findTop3ByIsMaleFalseOrderByPointsDesc());
    }

    @GetMapping("athletes/top3Men")
    public ResponseEntity<?> top3Men() {
        return ResponseEntity.ok(athletesRepository.findTop3ByIsMaleTrueOrderByPointsDesc());
    }
}
