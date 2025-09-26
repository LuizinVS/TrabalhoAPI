package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HabitatDto;

import java.util.List;
import java.util.Optional;

public interface HabitatService {

    HabitatDto create(HabitatDto dto);
    Optional<HabitatDto> update(Long id, HabitatDto dto);
    Optional<HabitatDto> findById(Long id);
    List<HabitatDto> listAll();
    void delete(Long id);

    List<HabitatDto> findByTipo(String tipo);
    List<HabitatDto> findByNome(String nome);

    HabitatDto toDto(com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat habitat);
    com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat toEntity(HabitatDto dto);
}