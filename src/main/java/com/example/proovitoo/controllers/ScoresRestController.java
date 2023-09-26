package com.example.proovitoo.controllers;

import com.example.proovitoo.dtos.ScorePutDto;
import com.example.proovitoo.entities.Score;
import com.example.proovitoo.repositories.ScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ScoresRestController {
    @Autowired
    private ScoresRepository scoresRepository;

     //Kasutades eraldi PutDTO-d välistatakse võimalus, et pahatahtlikult muudetakse athleteId või eventId ära.
    @PutMapping("score")
    public ResponseEntity<?> putScore(@RequestBody ScorePutDto scoreDto) {
        Optional<Score> optional = scoresRepository.findById(scoreDto.getId());
        if(optional.isEmpty()) {
            return new ResponseEntity<>("Such score does not exist.", HttpStatus.NOT_FOUND);
        }
        Score score = optional.get();
        score.setResult(scoreDto.getResult());
        score.getAthlete().recalculatePoints();
        scoresRepository.saveAndFlush(score);
        return ResponseEntity.ok(score);
    }
}
