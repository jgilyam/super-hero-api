package com.folcode.super_hero_api.domain.persistence.repositories;

import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHeroEntity,Integer>, JpaSpecificationExecutor<SuperHeroEntity> {
}
