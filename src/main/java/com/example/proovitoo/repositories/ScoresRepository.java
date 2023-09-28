package com.example.proovitoo.repositories;

import com.example.proovitoo.entities.Athlete;
import com.example.proovitoo.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ScoresRepository extends JpaRepository<Score, UUID> {
    public List<Score> findAllByResultIsNull();
}
