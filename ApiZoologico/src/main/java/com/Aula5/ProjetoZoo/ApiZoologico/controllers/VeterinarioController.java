package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.VeterinarioDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Veterinario;
import com.Aula5.ProjetoZoo.ApiZoologico.services.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @GetMapping
    public ResponseEntity<List<VeterinarioDto>> getAll() {
        List<VeterinarioDto> vets = veterinarioService.findAll()
                .stream()
                .map(veterinarioService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioDto> getById(@PathVariable Long id) {
        Veterinario veterinario = veterinarioService.findById(id);
        VeterinarioDto dto = veterinarioService.toDto(veterinario);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<VeterinarioDto>> getByEspecialidade(@PathVariable String especialidade) {
        List<VeterinarioDto> vets = veterinarioService.findByEspecialidade(especialidade)
                .stream()
                .map(veterinarioService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vets);
    }

    @PostMapping
    public ResponseEntity<VeterinarioDto> create(@RequestBody VeterinarioDto dto) {
        VeterinarioDto criado = veterinarioService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioDto> update(@PathVariable Long id, @RequestBody VeterinarioDto dto) {
        VeterinarioDto atualizado = veterinarioService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com ID " + id));
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        veterinarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}