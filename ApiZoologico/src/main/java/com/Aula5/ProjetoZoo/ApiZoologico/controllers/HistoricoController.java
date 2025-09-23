package com.Aula5.ProjetoZoo.ApiZoologico.controllers;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HistoricoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.services.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @GetMapping("/{animalId}")
    public ResponseEntity<HistoricoDto> getHistorico(@PathVariable Long animalId) {
        return ResponseEntity.ok(historicoService.gerarHistorico(animalId));
    }
}
