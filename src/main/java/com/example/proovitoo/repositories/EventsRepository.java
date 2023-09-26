package com.example.proovitoo.repositories;

import com.example.proovitoo.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventsRepository extends JpaRepository<Event, UUID> {
}
