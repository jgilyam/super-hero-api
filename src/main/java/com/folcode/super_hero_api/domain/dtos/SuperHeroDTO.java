package com.folcode.super_hero_api.domain.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuperHeroDTO {
    private Integer id;
    private String name;
    private String description;
    private String biography;
    private String alterEgo;
    private String species;
}
