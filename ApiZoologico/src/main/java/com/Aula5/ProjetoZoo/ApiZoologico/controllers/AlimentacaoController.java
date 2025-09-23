package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AlimentacaoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;
import com.Aula5.ProjetoZoo.ApiZoologico.services.AlimentacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alimentacao")
public class AlimentacaoController {

    private final AlimentacaoService alimentacaoService;

    public AlimentacaoController(AlimentacaoService alimentacaoService) {
        this.alimentacaoService = alimentacaoService;
    }

    @GetMapping
    public ResponseEntity<List<AlimentacaoDto>> getAll(
            @RequestParam(required = false) String tipoComida,
            @RequestParam(required = false) Long animalId) {

        if (tipoComida != null) {
            return ResponseEntity.ok(alimentacaoService.findDtoByTipoComida(tipoComida));
        } else if (animalId != null) {
            return ResponseEntity.ok(alimentacaoService.findDtoByAnimalId(animalId));
        } else {
            return ResponseEntity.ok(alimentacaoService.findAllDto());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(alimentacaoService.findDtoById(id));
        } catch (RuntimeException e) {
            return buildErrorResponse("Alimentação não encontrada", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AlimentacaoDto> add(@RequestBody AlimentacaoDto alimentacao) {
        AlimentacaoDto novaAlimentacao = alimentacaoService.create(alimentacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAlimentacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AlimentacaoDto alimentacao) {
        try {
            return ResponseEntity.ok(alimentacaoService.update(id, alimentacao));
        } catch (RuntimeException e) {
            return buildErrorResponse("Erro ao atualizar Alimentação", e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            alimentacaoService.delete(id);
            return ResponseEntity.ok("Alimentação removida com sucesso!");
        } catch (RuntimeException e) {
            return buildErrorResponse("Erro ao remover Alimentação", e.getMessage(), HttpStatus.NOT_FOUND);
        }
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