package com.example.proovitoo.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScorePutDto {
    private UUID id;
    private Double result;
}
