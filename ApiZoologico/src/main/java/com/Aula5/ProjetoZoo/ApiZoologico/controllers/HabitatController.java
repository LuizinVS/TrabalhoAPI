package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HabitatDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.services.HabitatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habitats")
@RequiredArgsConstructor
public class HabitatController {

    private final HabitatService habitatService;

    @GetMapping
    public ResponseEntity<List<HabitatDto>> listAll(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String nome) {
        List<HabitatDto> habitats;
        if (tipo != null) {
            habitats = habitatService.findByTipo(tipo);
        } else if (nome != null) {
            habitats = habitatService.findByNome(nome);
        } else {
            habitats = habitatService.listAll();
        }
        return ResponseEntity.ok(habitats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitatDto> findById(@PathVariable Long id) {
        HabitatDto dto = habitatService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitat não encontrado com ID " + id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<HabitatDto> create(@RequestBody HabitatDto dto) {
        HabitatDto criado = habitatService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitatDto> update(@PathVariable Long id, @RequestBody HabitatDto dto) {
        HabitatDto atualizado = habitatService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Habitat não encontrado com ID " + id));
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitatService.delete(id); 
        return ResponseEntity.noContent().build();
    }
}