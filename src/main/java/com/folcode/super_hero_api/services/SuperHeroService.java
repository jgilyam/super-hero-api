package com.folcode.super_hero_api.services;

import com.folcode.super_hero_api.domain.dtos.SuperHeroDTO;
import com.folcode.super_hero_api.domain.mappers.ISuperHeroMapper;
import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import com.folcode.super_hero_api.domain.persistence.repositories.SuperHeroRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuperHeroService {
    private final SuperHeroRepository superHeroRepository;
    private final ISuperHeroMapper superHeroMapper;

    public SuperHeroService(SuperHeroRepository superHeroRepository, ISuperHeroMapper superHeroMapper) {
        this.superHeroRepository = superHeroRepository;
        this.superHeroMapper = superHeroMapper;
    }

    public List<SuperHeroDTO> findAll() {
        return superHeroRepository.findAll()
                .stream()
                .map(superHeroMapper::superHeroEntityToSuperHeroDTO)
                .collect(Collectors.toList());
    }
}
