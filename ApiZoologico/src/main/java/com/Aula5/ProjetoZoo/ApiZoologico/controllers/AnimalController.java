package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AnimalDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Animal;
import com.Aula5.ProjetoZoo.ApiZoologico.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<List<Animal>> listAll(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) Integer idadeMin,
            @RequestParam(required = false) Integer idadeMax,
            @RequestParam(required = false) String nome) {

        if (especie != null) return ResponseEntity.ok(animalService.findByEspecieEntity(especie));
        if (idadeMin != null && idadeMax != null) return ResponseEntity.ok(animalService.findByIdadeBetweenEntity(idadeMin, idadeMax));
        if (nome != null) return ResponseEntity.ok(animalService.findByNomeEntity(nome));

        return ResponseEntity.ok(animalService.findAllEntities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(animalService.findByIdEntity(id));
        } catch (RuntimeException e) {
            return buildErrorResponse("Animal não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Animal> create(@RequestBody AnimalDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.createEntity(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AnimalDto dto) {
        try {
            return ResponseEntity.ok(animalService.updateEntity(id, dto));
        } catch (RuntimeException e) {
            return buildErrorResponse("Erro ao atualizar animal", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            animalService.delete(id);
            return ResponseEntity.ok("Animal removido com sucesso!");
        } catch (RuntimeException e) {
            return buildErrorResponse("Erro ao remover animal", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String error, String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
