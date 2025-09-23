package com.Aula5.ProjetoZoo.ApiZoologico.dtos;

public record AnimalDto(
        Long id,
        String nome,
        String especie,
        int idade,
        Long habitatId,
        Long cuidadorId
) {}
