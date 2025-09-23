package com.Aula5.ProjetoZoo.ApiZoologico.dtos;

import java.util.List;

public record HistoricoDto(
        Long animalId,
        String nome,
        String especie,
        int idade,
        List<String> alimentacoes,
        List<String> consultas
) {}
