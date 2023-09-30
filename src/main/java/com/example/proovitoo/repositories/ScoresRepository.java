package com.example.proovitoo.repositories;

import com.example.proovitoo.entities.Athlete;
import com.example.proovitoo.entities.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ScoresRepository extends JpaRepository<Score, UUID> {
    @Query("select distinct a from Score s join s.athlete a where s.result is null order by a.timeCreated desc")
    public Page<Athlete> findAllAthletesByResultIsNullOrderByTimeCreatedDesc(Pageable pageable);
}
