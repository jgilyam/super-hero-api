package com.folcode.super_hero_api.services;

import com.folcode.super_hero_api.domain.dtos.SuperHeroDTO;
import com.folcode.super_hero_api.domain.mappers.ISuperHeroMapper;
import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import com.folcode.super_hero_api.domain.persistence.repositories.SuperHeroRepository;
import com.folcode.super_hero_api.domain.persistence.specifications.ISuperHeroSpecification;
import com.folcode.super_hero_api.domain.persistence.specifications.SuperHeroSpecification;
import com.folcode.super_hero_api.exceptions.SuperHeroNotFoundExceptions;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class SuperHeroServiceTest {
    @Mock
    private SuperHeroRepository superHeroRepositoryMock;

    @Mock
    private  ISuperHeroMapper superHeroMapperMock;

    private SuperHeroService superHeroService;

    @Mock
    private ISuperHeroSpecification superHeroSpecificationsMock;



    @BeforeEach
    public void setup() {
        superHeroService = new SuperHeroService(superHeroRepositoryMock, superHeroMapperMock,superHeroSpecificationsMock);
    }

    @Test
    public void shouldReturnAllSuperHeroes() {
        List<SuperHeroEntity> superHeroesExpected = new ArrayList<>();
        superHeroesExpected.add(new SuperHeroEntity(1, "Spiderman", null, null, null, null));
        superHeroesExpected.add(new SuperHeroEntity(2, "Spiderman2", null, null, null, null));

        Specification<SuperHeroEntity> whereMock= (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        Mockito
                .when(superHeroSpecificationsMock.findAll(null))
                .thenReturn(whereMock);

        Mockito
                .when(superHeroRepositoryMock.findAll(whereMock))
                .thenReturn(superHeroesExpected);
        Mockito
                .when(superHeroMapperMock.superHeroEntityToSuperHeroDTO(any(SuperHeroEntity.class)))
                .thenReturn(new SuperHeroDTO());


        List<SuperHeroDTO> superHeroesReturned = superHeroService.findAllSuperHeroes(null);

        Assertions.assertThat(superHeroesReturned.size()).isEqualTo(superHeroesExpected.size());


    }

    @Test
    public void shouldReturnOneSuperHeroById() {
        SuperHeroEntity mockEntity = Mockito.mock(SuperHeroEntity.class);
        SuperHeroDTO mockDto = Mockito.mock(SuperHeroDTO.class);

        Mockito
                .when(superHeroRepositoryMock.findById(42))
                .thenReturn(Optional.ofNullable(mockEntity));

        Mockito
                .when(superHeroMapperMock.superHeroEntityToSuperHeroDTO(mockEntity))
                .thenReturn(mockDto);



        SuperHeroDTO superHero = superHeroService.findSuperHeroById(42);

        Assertions.assertThat(superHero)
                .isSameAs(mockDto);
    }

    @Test
    public void shouldThrowSuperHeroNotFoundException() {
        SuperHeroEntity mockEntity = Mockito.mock(SuperHeroEntity.class);

        Mockito
                .when(superHeroRepositoryMock.findById(43))
                .thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(()->superHeroService.findSuperHeroById(43))
                .isInstanceOf(SuperHeroNotFoundExceptions.class);

    }
    @Test
    public void shouldReturnAllSuperHeroesByName(){
        List<SuperHeroEntity> superHeroesExpected = new ArrayList<>();
        superHeroesExpected.add(new SuperHeroEntity(1, "Spiderman", null, null, null, null));
        superHeroesExpected.add(new SuperHeroEntity(2, "Superman", null, null, null, null));
        superHeroesExpected.add(new SuperHeroEntity(3, "Manolito el fuerte", null, null, null, null));

        List<SuperHeroDTO> superHeroesDtoExpected = new ArrayList<>();
        superHeroesDtoExpected.add(new SuperHeroDTO(1, "Spiderman", null, null, null, null));
        superHeroesDtoExpected.add(new SuperHeroDTO(2, "Superman", null, null, null, null));
        superHeroesDtoExpected.add(new SuperHeroDTO(3, "Manolito el fuerte", null, null, null, null));//superHeroesDtoExpected.add(new SuperHeroDTO(4, "Dr. Strange", null, null, null, null));

        String queryName = "man";

        Specification<SuperHeroEntity> whereMock= (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.conjunction(), criteriaBuilder.like(criteriaBuilder.lower(root.get("name")).as(String.class), "%"+queryName.toLowerCase()+"%"));

        Mockito
                .when(superHeroSpecificationsMock.findAll(queryName))
                .thenReturn(whereMock);

        Mockito
                .when(superHeroRepositoryMock.findAll(whereMock))
                .thenReturn(superHeroesExpected);

        for (int i = 0; i < superHeroesExpected.size(); i++) {
            Mockito
                    .when(superHeroMapperMock.superHeroEntityToSuperHeroDTO(superHeroesExpected.get(i)))
                    .thenReturn(superHeroesDtoExpected.get(i));

        }



        List<SuperHeroDTO> superHeroesReturned = superHeroService.findAllSuperHeroes(queryName);


        Assertions
                .assertThat(superHeroesReturned)
                .extracting(SuperHeroDTO::getId)
                .containsOnly(1,2,3);


    }
}