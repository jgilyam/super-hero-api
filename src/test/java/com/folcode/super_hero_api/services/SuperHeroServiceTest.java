package com.folcode.super_hero_api.services;

import com.folcode.super_hero_api.domain.dtos.SuperHeroDTO;
import com.folcode.super_hero_api.domain.mappers.ISuperHeroMapper;
import com.folcode.super_hero_api.domain.persistence.entities.SuperHeroEntity;
import com.folcode.super_hero_api.domain.persistence.repositories.SuperHeroRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class SuperHeroServiceTest {
    @Mock
    private SuperHeroRepository superHeroRepositoryMock;
    @Mock
    private ISuperHeroMapper superHeroMapperMock;
    private SuperHeroService superHeroService;
    @BeforeEach
    public void setup(){
        superHeroService = new SuperHeroService(superHeroRepositoryMock, superHeroMapperMock);
    }

    @Test
    public void shouldReturnAllSuperHeroes(){
        List<SuperHeroEntity> superHeroesExpected= new ArrayList<>();
        superHeroesExpected.add(new SuperHeroEntity(1,"Spiderman",null,null,null,null));
        superHeroesExpected.add(new SuperHeroEntity(2,"Spiderman2",null,null,null,null));

        Mockito
                .when(superHeroRepositoryMock.findAll())
                .thenReturn(superHeroesExpected);

        Mockito
                .when(superHeroMapperMock.superHeroEntityToSuperHeroDTO(any(SuperHeroEntity.class)))
                .thenReturn(new SuperHeroDTO());

        List<SuperHeroDTO> superHeroesReturned = superHeroService.findAll();

        Assertions.assertThat(superHeroesReturned.size()).isEqualTo(superHeroesExpected.size());


    }
    @Test
    public void shouldReturnOneSuperHeroById(){
        SuperHeroEntity mockEntity = Mockito.mock(SuperHeroEntity.class);
        SuperHeroDTO mockDto = Mockito.mock(SuperHeroDTO.class);

        Mockito
                .when(superHeroRepositoryMock.findById(42))
                .thenReturn(Optional.ofNullable(mockEntity));
        Mockito
                .when(superHeroMapperMock.superHeroEntityToSuperHeroDTO(mockEntity))
                .thenReturn(mockDto);


        SuperHeroDTO superHero = superHeroService.findById(42);

        Assertions.assertThat(superHero)
                .isSameAs(mockDto);
    }


}
