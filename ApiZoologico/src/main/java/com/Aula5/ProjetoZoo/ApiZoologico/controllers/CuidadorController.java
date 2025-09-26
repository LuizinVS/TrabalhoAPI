package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.CuidadorDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Turno;
import com.Aula5.ProjetoZoo.ApiZoologico.services.CuidadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuidadores")
@RequiredArgsConstructor
public class CuidadorController {

    private final CuidadorService cuidadorService;

    @GetMapping
    public ResponseEntity<List<CuidadorDto>> listAll(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) Turno turno) {

        List<CuidadorDto> list = cuidadorService.filtrarCuidadores(especialidade, turno);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuidadorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuidadorService.findById(id)
                .orElseThrow(() -> new com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException("Cuidador não encontrado com ID " + id)));
    }

    @PostMapping
    public ResponseEntity<CuidadorDto> create(@RequestBody CuidadorDto dto) {
        return ResponseEntity.status(201).body(cuidadorService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuidadorDto> update(@PathVariable Long id, @RequestBody CuidadorDto dto) {
        return ResponseEntity.ok(cuidadorService.update(id, dto)
                .orElseThrow(() -> new com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException("Cuidador não encontrado com ID " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuidadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}