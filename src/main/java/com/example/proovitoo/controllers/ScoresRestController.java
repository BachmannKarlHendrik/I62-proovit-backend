package com.example.proovitoo.controllers;

import com.example.proovitoo.dtos.ScorePutDto;
import com.example.proovitoo.entities.Athlete;
import com.example.proovitoo.entities.Score;
import com.example.proovitoo.repositories.ScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

    //Kasutades eraldi PutDTO-d välistatakse võimalus, et pahatahtlikult muudetakse athleteId või eventId ära.
    @PutMapping("scores")
    public ResponseEntity<?> putScores(@RequestBody List<ScorePutDto> scoresList) {
        if(scoresList.isEmpty()) {
            return ResponseEntity.ok(scoresList);
        }

        List<Score> editedScores = new ArrayList<>();
        Athlete athlete = null;

        for (ScorePutDto scorePutDto : scoresList) {
            Optional<Score> optional = scoresRepository.findById(scorePutDto.getId());
            if(scorePutDto.getResult() < 0) {
                return new ResponseEntity<>(scorePutDto + " result is smaller than 0.",HttpStatus.BAD_REQUEST);
            }
            if(optional.isEmpty()) {
                return new ResponseEntity<>(scorePutDto + " no such score found.", HttpStatus.NOT_FOUND);
            }
            Score score = optional.get();
            if(athlete == null) {
                athlete = optional.get().getAthlete();
            }
            //Välistatakse, et kogemata on mõne muu võistleja tulemused samuti üle kirjutamisele sattunud.
            if(!athlete.getId().equals(score.getAthlete().getId())) {
                return new ResponseEntity<>("All scores must have the same athlete!",HttpStatus.BAD_REQUEST);
            }

            //Kui kõik testid on läbitud, lisatakse muudetud skoor muudetavate listi.
            score.setResult(scorePutDto.getResult());
            editedScores.add(score);
            System.out.println(score.getEvent().getNameEst()+": "+score.getResult());
        }

        editedScores.get(0).getAthlete().recalculatePoints();
        scoresRepository.saveAllAndFlush(editedScores);
        return ResponseEntity.ok(editedScores);
    }
}
