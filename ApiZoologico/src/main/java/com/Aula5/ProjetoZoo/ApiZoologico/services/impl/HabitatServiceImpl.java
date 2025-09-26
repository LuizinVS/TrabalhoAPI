package com.Aula5.ProjetoZoo.ApiZoologico.services.impl;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HabitatDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.HabitatRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.services.HabitatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitatServiceImpl implements HabitatService {

    private final HabitatRepository repository;

    @Override
    public HabitatDto create(HabitatDto dto) {
        Habitat habitat = toEntity(dto);
        return toDto(repository.save(habitat));
    }

    @Override
    public Optional<HabitatDto> update(Long id, HabitatDto dto) {
        Habitat habitat = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitat não encontrado com id " + id));

        habitat.setNome(dto.nome());
        habitat.setTipo(dto.tipo());
        habitat.setCapacidadeMaxima(dto.capacidadeMaxima());

        return Optional.of(toDto(repository.save(habitat)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HabitatDto> findById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitatDto> listAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Habitat habitat = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitat não encontrado com id " + id));
        repository.delete(habitat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitatDto> findByTipo(String tipo) {
        return repository.findByTipoIgnoreCase(tipo).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitatDto> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public HabitatDto toDto(Habitat habitat) {
        return new HabitatDto(
                habitat.getId(),
                habitat.getNome(),
                habitat.getTipo(),
                habitat.getCapacidadeMaxima()
        );
    }

    @Override
    public Habitat toEntity(HabitatDto dto) {
        Habitat habitat = new Habitat();
        habitat.setNome(dto.nome());
        habitat.setTipo(dto.tipo());
        habitat.setCapacidadeMaxima(dto.capacidadeMaxima());
        return habitat;
    }
}