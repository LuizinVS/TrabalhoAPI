package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AnimalDto;

import java.util.List;
import java.util.Optional;

public interface AnimalService {
    AnimalDto create(AnimalDto dto);
    Optional<AnimalDto> update(Long id, AnimalDto dto);
    Optional<AnimalDto> findById(Long id);
    List<AnimalDto> listAll();
    void delete(Long id);

    List<AnimalDto> findByEspecie(String especie);
    List<AnimalDto> findByIdadeBetween(int min, int max);
    List<AnimalDto> findByNome(String nome);

    AnimalDto toDto(com.Aula5.ProjetoZoo.ApiZoologico.models.Animal animal);
    com.Aula5.ProjetoZoo.ApiZoologico.models.Animal toEntity(AnimalDto dto);
}