package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AlimentacaoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;

import java.util.List;
import java.util.Optional;

public interface AlimentacaoService {
    AlimentacaoDto create(AlimentacaoDto dto);
    Optional<AlimentacaoDto> update(Long id, AlimentacaoDto dto);
    Optional<AlimentacaoDto> findById(Long id);
    List<AlimentacaoDto> listAll();
    void delete(Long id);
    List<AlimentacaoDto> findByTipoComida(String tipo);
    List<AlimentacaoDto> findByAnimalId(Long animalId);

    AlimentacaoDto toDto(Alimentacao alimentacao);
    Alimentacao toEntity(AlimentacaoDto dto);
}