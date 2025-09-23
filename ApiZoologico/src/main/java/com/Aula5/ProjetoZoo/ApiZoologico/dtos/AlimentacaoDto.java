package com.Aula5.ProjetoZoo.ApiZoologico.dtos;


import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;

import java.time.LocalDateTime;

public record AlimentacaoDto (
        Long id,
        Alimentacao.TipoComida tipoComida,
        double quantidadeDiaria,
        LocalDateTime horario,
        boolean realizada,
        Long animalId,
        String animalNome,
        String cuidadorNome
){}
