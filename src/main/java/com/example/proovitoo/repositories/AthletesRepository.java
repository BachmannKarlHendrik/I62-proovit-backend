package com.example.proovitoo.repositories;

import com.example.proovitoo.entities.Athlete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AthletesRepository extends JpaRepository<Athlete, UUID> {
    public List<Athlete> findAllByOrderByTimeCreatedDesc();
    public Page<Athlete> findAllByOrderByTimeCreatedDesc(Pageable pageable);
    public Page<Athlete> findAllByIdInOrderByTimeCreatedDesc(List<UUID> ids, Pageable pageable);
}
