package com.folcode.super_hero_api.domain.persistence.specifications;

import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SuperHeroSpecification implements ISuperHeroSpecification {
    public Specification<SuperHeroEntity> findAll(String name) {
        return (root, query, criteriaBuilder) -> findByAllQueryBuilder(root, criteriaBuilder, name);
    }
    private Predicate findByAllQueryBuilder(Root<SuperHeroEntity> agencyMetadata, CriteriaBuilder criteriaBuilder, String name){

        Predicate where = criteriaBuilder.conjunction();

        List<Predicate> predicates = new ArrayList<>();

        Optional
                .ofNullable(name)
                .ifPresent(value -> predicates.add(namePredicate(agencyMetadata, criteriaBuilder, value)));

        for (Predicate predicate : predicates)
            where = criteriaBuilder.and(where, predicate);

        return where;
    }

    private Predicate namePredicate(Root<SuperHeroEntity> root, CriteriaBuilder criteriaBuilder, String name) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")).as(String.class), "%"+name.toLowerCase()+"%");
    }
}
