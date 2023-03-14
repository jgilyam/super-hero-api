package com.folcode.super_hero_api.services;

import com.folcode.super_hero_api.domain.dtos.SuperHeroDTO;
import com.folcode.super_hero_api.domain.mappers.ISuperHeroMapper;
import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import com.folcode.super_hero_api.domain.persistence.repositories.SuperHeroRepository;
import com.folcode.super_hero_api.domain.persistence.specifications.ISuperHeroSpecification;
import com.folcode.super_hero_api.domain.persistence.specifications.SuperHeroSpecification;
import com.folcode.super_hero_api.exceptions.SuperHeroBadRequestExceptions;
import com.folcode.super_hero_api.exceptions.SuperHeroNotFoundExceptions;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperHeroService {
    private final SuperHeroRepository superHeroRepository;
    private final ISuperHeroMapper superHeroMapper;
    private final ISuperHeroSpecification superHeroSpecifications;

    public SuperHeroService(SuperHeroRepository superHeroRepository, ISuperHeroMapper superHeroMapper, ISuperHeroSpecification superHeroSpecifications) {
        this.superHeroRepository = superHeroRepository;
        this.superHeroMapper = superHeroMapper;
        this.superHeroSpecifications = superHeroSpecifications;
    }

    public List<SuperHeroDTO> findAllSuperHeroes(String name) {
        Specification<SuperHeroEntity> where = superHeroSpecifications.findAll(name);
        return superHeroRepository.findAll(where)
                .stream()
                .map(superHeroMapper::superHeroEntityToSuperHeroDTO)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public SuperHeroDTO findSuperHeroById(Integer superHeroId) {
        SuperHeroEntity superHeroEntity = existsSuperHero(superHeroId);
        return superHeroMapper.superHeroEntityToSuperHeroDTO(superHeroEntity);
    }

    @SneakyThrows
    public SuperHeroDTO addSuperHero(SuperHeroDTO superHeroDTO){
        return Optional
                .ofNullable(superHeroDTO)
                .map(superHeroMapper::superHeroDTOToSuperHeroEntity)
                .map(superHeroRepository::save)
                .map(superHeroMapper::superHeroEntityToSuperHeroDTO)
                .orElseThrow(SuperHeroBadRequestExceptions::new);

    }
    @SneakyThrows
    public SuperHeroDTO editSuperHero(SuperHeroDTO superHeroDTO){
        existsSuperHero(superHeroDTO.getId());
        return Optional
                .of(superHeroDTO)
                .map(superHeroMapper::superHeroDTOToSuperHeroEntity)
                .map(superHeroRepository::save)
                .map(superHeroMapper::superHeroEntityToSuperHeroDTO)
                .orElseThrow(SuperHeroNotFoundExceptions::new);
    }

    @SneakyThrows
    private SuperHeroEntity existsSuperHero(Integer superHeroId){
        return Optional
                .ofNullable(superHeroId)
                .map(superHeroRepository::findById)
                .map(optionalSuperHeroEntity->optionalSuperHeroEntity.orElseThrow(SuperHeroNotFoundExceptions::new))
                .orElseThrow(SuperHeroBadRequestExceptions::new);

    }
    public SuperHeroDTO deleteSuperHeroById(Integer superHeroId){
        SuperHeroDTO superHeroDTO = superHeroMapper.superHeroEntityToSuperHeroDTO(existsSuperHero(superHeroId));
        superHeroRepository.deleteById(superHeroId);
        return  superHeroDTO;
    }
}
