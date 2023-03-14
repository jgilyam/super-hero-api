package com.folcode.super_hero_api.domain.persistence.specifications;

import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import org.springframework.data.jpa.domain.Specification;

public interface ISuperHeroSpecification {
    Specification<SuperHeroEntity> findAll(String name);
}
