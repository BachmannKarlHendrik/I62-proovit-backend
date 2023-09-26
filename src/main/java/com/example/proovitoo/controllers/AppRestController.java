package com.example.proovitoo.controllers;

import com.example.proovitoo.entities.Event;
import com.example.proovitoo.repositories.AthletesRepository;
import com.example.proovitoo.repositories.EventsRepository;
import com.example.proovitoo.repositories.ScoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRestController {
    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private AthletesRepository athletesRepository;

    @Autowired
    private ScoresRepository scoresRepository;

    @GetMapping("test")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok(scoresRepository.findAll());
    }
}
