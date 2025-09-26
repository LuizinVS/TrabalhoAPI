package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.CuidadorDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Turno;

import java.util.List;
import java.util.Optional;

public interface CuidadorService {

    CuidadorDto create(CuidadorDto dto);
    Optional<CuidadorDto> update(Long id, CuidadorDto dto);
    Optional<CuidadorDto> findById(Long id);
    List<CuidadorDto> listAll();
    void delete(Long id);

    List<CuidadorDto> filtrarCuidadores(String especialidade, Turno turno);

    CuidadorDto toDto(com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador cuidador);
    com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador toEntity(CuidadorDto dto);
}