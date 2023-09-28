package com.example.proovitoo.repositories;

import com.example.proovitoo.entities.Athlete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AthletesRepository extends JpaRepository<Athlete, UUID> {
    public List<Athlete> findAllByOrderByTimeCreatedDesc();
    public Page<Athlete> findAllByOrderByTimeCreatedDesc(Pageable pageable);
    public Page<Athlete> findAllByIdInOrderByTimeCreatedDesc(List<UUID> ids, Pageable pageable);

    //Peab defineerima eraldi query. Muidu toimub top 3 enne kui order by.
    @Query("select a from Athlete a where a.isMale = false order by a.points desc limit 3")
    public List<Athlete> findTop3ByIsMaleFalseOrderByPointsDesc();
    @Query("select a from Athlete a where a.isMale = true order by a.points desc limit 3")
    public List<Athlete> findTop3ByIsMaleTrueOrderByPointsDesc();
}
