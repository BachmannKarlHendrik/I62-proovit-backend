package com.example.proovitoo.repositories;

import com.example.proovitoo.entities.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AthletesRepository extends JpaRepository<Athlete, UUID> {
    public List<Athlete> findAllByOrderByTimeCreatedDesc();
}
