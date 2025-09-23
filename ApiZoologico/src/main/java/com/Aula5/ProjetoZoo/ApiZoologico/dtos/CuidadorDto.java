package com.Aula5.ProjetoZoo.ApiZoologico.dtos;

import com.Aula5.ProjetoZoo.ApiZoologico.models.Turno;

public record CuidadorDto(
        Long id,
        String nome,
        String especialidade,
        Turno turno,
        String email,
        String telefone
) {
}