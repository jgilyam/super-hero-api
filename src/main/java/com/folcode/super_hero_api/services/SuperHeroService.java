package com.folcode.super_hero_api.services;

import com.folcode.super_hero_api.domain.dtos.SuperHeroDTO;
import com.folcode.super_hero_api.domain.mappers.ISuperHeroMapper;
import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import com.folcode.super_hero_api.domain.persistence.repositories.SuperHeroRepository;
import com.folcode.super_hero_api.domain.persistence.specifications.SuperHeroSpecification;
import com.folcode.super_hero_api.exceptions.SuperHeroNotFoundExceptions;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuperHeroService {
    private final SuperHeroRepository superHeroRepository;
    private final ISuperHeroMapper superHeroMapper;
    private final SuperHeroSpecification superHeroSpecifications;

    public SuperHeroService(SuperHeroRepository superHeroRepository, ISuperHeroMapper superHeroMapper, SuperHeroSpecification superHeroSpecifications) {
        this.superHeroRepository = superHeroRepository;
        this.superHeroMapper = superHeroMapper;
        this.superHeroSpecifications = superHeroSpecifications;
    }

    public List<SuperHeroDTO> findAll(String name) {
        Specification<SuperHeroEntity> where = superHeroSpecifications.findAll(name);
        return superHeroRepository.findAll(where)
                .stream()
                .map(superHeroMapper::superHeroEntityToSuperHeroDTO)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public SuperHeroDTO findById(int superHeroId) {
        return superHeroRepository
                .findById(superHeroId)
                .map(superHeroMapper::superHeroEntityToSuperHeroDTO)
                .orElseThrow(SuperHeroNotFoundExceptions::new);
    }
}
