package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AlimentacaoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;
import com.Aula5.ProjetoZoo.ApiZoologico.services.AlimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alimentacoes")
@RequiredArgsConstructor
public class AlimentacaoController {

    private final AlimentacaoService alimentacaoService;

    @GetMapping
    public ResponseEntity<List<AlimentacaoDto>> listAll(
            @RequestParam(required = false) String tipoComida,
            @RequestParam(required = false) Long animalId) {

        List<AlimentacaoDto> list;

        if (tipoComida != null) {
            list = alimentacaoService.findByTipoComida(tipoComida);
        } else if (animalId != null) {
            list = alimentacaoService.findByAnimalId(animalId);
        } else {
            list = alimentacaoService.listAll();
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentacaoDto> getById(@PathVariable Long id) {
        AlimentacaoDto dto = alimentacaoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimentação não encontrada com ID " + id));
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AlimentacaoDto> create(@RequestBody AlimentacaoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alimentacaoService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentacaoDto> update(@PathVariable Long id, @RequestBody AlimentacaoDto dto) {
                AlimentacaoDto atualizado = alimentacaoService.update(id, dto)
        .orElseThrow(() -> new ResourceNotFoundException("Alimentação não encontrada com ID" +id));
                return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alimentacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}