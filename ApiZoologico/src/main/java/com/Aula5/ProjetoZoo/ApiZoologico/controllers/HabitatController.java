package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat;
import com.Aula5.ProjetoZoo.ApiZoologico.services.HabitatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/habitats")
@RequiredArgsConstructor
public class HabitatController {

    private final HabitatService habitatService;

    @GetMapping
    public ResponseEntity<List<Habitat>> listAll(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String nome) {
        List<Habitat> habitats;
        if (tipo != null) {
            habitats = habitatService.findByTipo(tipo);
        } else if (nome != null) {
            habitats = habitatService.findByNome(nome);
        } else {
            habitats = habitatService.findAll();
        }
        return ResponseEntity.ok(habitats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Habitat habitat = habitatService.findById(id);
            return ResponseEntity.ok(habitat);
        } catch (RuntimeException e) {
            return buildErrorResponse("Habitat não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Habitat> create(@RequestBody Habitat habitat) {
        Habitat criado = habitatService.create(habitat);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Habitat habitat) {
        try {
            Habitat atualizado = habitatService.update(id, habitat);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return buildErrorResponse("Habitat não encontrado", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitatService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String error, String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
