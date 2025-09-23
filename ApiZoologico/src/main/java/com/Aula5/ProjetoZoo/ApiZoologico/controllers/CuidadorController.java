package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.CuidadorDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Turno;
import com.Aula5.ProjetoZoo.ApiZoologico.services.CuidadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cuidadores")
@RequiredArgsConstructor
public class CuidadorController {
    private final CuidadorService cuidadorService;

    @GetMapping
    public ResponseEntity<?> listAll(
            @RequestParam(required = false) String especialidade,
            @RequestParam(required = false) Turno turno) {

        try {
            List<CuidadorDto> cuidadores = cuidadorService.filtrarCuidadores(especialidade, turno);
            return ResponseEntity.ok(cuidadores);
        } catch (Exception e) {
            return buildErrorResponse("Erro ao listar cuidadores", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<CuidadorDto> cuidadorOptional = cuidadorService.findById(id);
        if (cuidadorOptional.isPresent()) {
            return ResponseEntity.ok(cuidadorOptional.get());
        } else {
            return buildErrorResponse("Cuidador não encontrado",
                    "Não existe cuidador com ID " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CuidadorDto dto) {
        try {
            CuidadorDto criado = cuidadorService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (Exception e) {
            return buildErrorResponse("Erro ao criar cuidador", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CuidadorDto dto) {
        try {
            Optional<CuidadorDto> cuidadorAtualizado = cuidadorService.update(id, dto);

            if (cuidadorAtualizado.isPresent()) {
                return ResponseEntity.ok(cuidadorAtualizado.get());
            } else {
                return buildErrorResponse(
                        "Cuidador não encontrado",
                        "Não existe cuidador com ID " + id,
                        HttpStatus.NOT_FOUND
                );
            }
        } catch (Exception e) {
            return buildErrorResponse(
                    "Erro ao atualizar cuidador",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            boolean deleted = cuidadorService.delete(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return buildErrorResponse("Cuidador não encontrado",
                        "Não existe cuidador com ID " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return buildErrorResponse("Erro ao deletar cuidador", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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