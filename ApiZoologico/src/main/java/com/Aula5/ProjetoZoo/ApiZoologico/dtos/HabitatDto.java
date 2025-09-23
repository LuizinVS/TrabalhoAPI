package com.Aula5.ProjetoZoo.ApiZoologico.dtos;

public record HabitatDto(
        Long id,
        String nome,
        String tipo,
        Integer capacidadeMaxima
){}