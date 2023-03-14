package com.folcode.super_hero_api.controllers;


import com.folcode.super_hero_api.domain.dtos.SuperHeroDTO;
import com.folcode.super_hero_api.services.SuperHeroService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "superHeroes")
@Validated
public class SuperHeroController {
    private final SuperHeroService superHeroService;

    public SuperHeroController(SuperHeroService superHeroService) {
        this.superHeroService = superHeroService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuperHeroDTO> add(
            @RequestBody @NotNull @Valid SuperHeroDTO superHeroDTO
    ) {
        return ResponseEntity
                .ok(superHeroService.addSuperHero(superHeroDTO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SuperHeroDTO>> findAll(@RequestParam(required = false) String name) {
        return ResponseEntity
                .ok(superHeroService.findAllSuperHeroes(name));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuperHeroDTO> edit(
            @RequestBody @NotNull @Valid SuperHeroDTO superHeroDTO
    ) {
        return ResponseEntity
                .ok(superHeroService.editSuperHero(superHeroDTO));
    }

    @DeleteMapping(value = "{superHeroId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuperHeroDTO> delete(
            @PathVariable @NotNull @Positive @Valid Integer superHeroId
    ) {
        return ResponseEntity
                .ok(superHeroService.deleteSuperHeroById(superHeroId));
    }

    @GetMapping(value = "{superHeroId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuperHeroDTO> findById(
            @PathVariable @NotNull @Positive @Valid Integer superHeroId
    ) {
        return ResponseEntity
                .ok(superHeroService.findSuperHeroById(superHeroId));
    }
}
