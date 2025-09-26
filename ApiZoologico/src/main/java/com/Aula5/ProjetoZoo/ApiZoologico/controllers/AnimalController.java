package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AnimalDto;
import com.Aula5.ProjetoZoo.ApiZoologico.services.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animais")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    public ResponseEntity<List<AnimalDto>> listAll(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) Integer idadeMin,
            @RequestParam(required = false) Integer idadeMax,
            @RequestParam(required = false) String nome) {

        List<AnimalDto> list;

        if (especie != null) {
            list = animalService.findByEspecie(especie);
        } else if (idadeMin != null && idadeMax != null) {
            list = animalService.findByIdadeBetween(idadeMin, idadeMax);
        } else if (nome != null) {
            list = animalService.findByNome(nome);
        } else {
            list = animalService.listAll();
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.findById(id).orElseThrow(
                () -> new com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException("Animal não encontrado com ID " + id)
        ));
    }

    @PostMapping
    public ResponseEntity<AnimalDto> create(@RequestBody AnimalDto dto) {
        return ResponseEntity.status(201).body(animalService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> update(@PathVariable Long id, @RequestBody AnimalDto dto) {
        return ResponseEntity.ok(animalService.update(id, dto)
                .orElseThrow(() -> new com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException("Animal não encontrado com ID " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}