package com.example.proovitoo.repositories;

import com.example.proovitoo.entities.Athlete;
import com.example.proovitoo.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScoresRepository extends JpaRepository<Score, UUID> {
}
