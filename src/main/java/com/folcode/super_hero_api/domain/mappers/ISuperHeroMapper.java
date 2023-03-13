package com.folcode.super_hero_api.domain.mappers;

import com.folcode.super_hero_api.domain.dtos.SuperHeroDTO;
import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISuperHeroMapper {
    SuperHeroEntity superHeroDTOToSuperHeroEntity(SuperHeroDTO superHeroDTO);
    SuperHeroDTO superHeroEntityToSuperHeroDTO(SuperHeroEntity superHeroEntity);
}
