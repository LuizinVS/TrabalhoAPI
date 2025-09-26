package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.VeterinarioDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Veterinario;

import java.util.List;
import java.util.Optional;

public interface VeterinarioService {

    VeterinarioDto create(VeterinarioDto dto);
    Optional<VeterinarioDto> update(Long id, VeterinarioDto dto);
    Optional<VeterinarioDto> findByIdDto(Long id);
    Veterinario findById(Long id);
    List<Veterinario> findAll();
    void delete(Long id);

    List<Veterinario> findByEspecialidade(String especialidade);

    VeterinarioDto toDto(Veterinario veterinario);
    Veterinario toEntity(VeterinarioDto dto);
}