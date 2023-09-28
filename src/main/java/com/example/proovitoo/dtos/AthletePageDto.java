package com.example.proovitoo.dtos;

import com.example.proovitoo.entities.Athlete;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AthletePageDto {
    private List<Athlete> athletes;
    private Integer currentPage;
    private Integer totalPages;

    public static AthletePageDto createFromPage(Page<Athlete> page) {
        AthletePageDto apd = new AthletePageDto();
        apd.setTotalPages(page.getTotalPages());
        apd.setCurrentPage(page.getNumber()+1);
        apd.setAthletes(page.getContent());
        return apd;
    }
}
