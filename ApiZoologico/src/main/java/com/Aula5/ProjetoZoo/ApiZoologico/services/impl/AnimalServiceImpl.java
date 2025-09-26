package com.Aula5.ProjetoZoo.ApiZoologico.services.impl;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AnimalDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Animal;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AnimalRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.CuidadorRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.HabitatRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.services.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository repository;
    private final CuidadorRepository cuidadorRepository;
    private final HabitatRepository habitatRepository;

    @Override
    public AnimalDto create(AnimalDto dto) {
        Animal animal = toEntity(dto);
        return toDto(repository.save(animal));
    }

    @Override
    public Optional<AnimalDto> update(Long id, AnimalDto dto) {
        Animal animal = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado com id " + id));

        animal.setNome(dto.nome());
        animal.setEspecie(dto.especie());
        animal.setIdade(dto.idade());

        if(dto.cuidadorId() != null) {
            Cuidador cuidador = cuidadorRepository.findById(dto.cuidadorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cuidador não encontrado com id " + dto.cuidadorId()));
            animal.setCuidador(cuidador);
        }

        if(dto.habitatId() != null) {
            Habitat habitat = habitatRepository.findById(dto.habitatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Habitat não encontrado com id " + dto.habitatId()));
            animal.setHabitat(habitat);
        }

        return Optional.of(toDto(repository.save(animal)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnimalDto> findById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnimalDto> listAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Animal animal = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado com ID " + id));
        repository.delete(animal);
    }

    @Override
    public List<AnimalDto> findByEspecie(String especie) {
        return repository.findByEspecie(especie).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findByIdadeBetween(int min, int max) {
        return repository.findByIdadeBetween(min, max).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AnimalDto> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public AnimalDto toDto(Animal animal) {
        return new AnimalDto(
                animal.getId(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getIdade(),
                animal.getHabitat() != null ? animal.getHabitat().getId() : null,
                animal.getCuidador() != null ? animal.getCuidador().getId() : null,
                animal.getCuidador() != null ? animal.getCuidador().getNome() : null
        );
    }

    @Override
    public Animal toEntity(AnimalDto dto) {
        Animal animal = new Animal();
        animal.setNome(dto.nome());
        animal.setEspecie(dto.especie());
        animal.setIdade(dto.idade());

        if(dto.cuidadorId() != null) {
            Cuidador cuidador = cuidadorRepository.findById(dto.cuidadorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cuidador não encontrado com id " + dto.cuidadorId()));
            animal.setCuidador(cuidador);
        }

        if(dto.habitatId() != null) {
            Habitat habitat = habitatRepository.findById(dto.habitatId())
                    .orElseThrow(() -> new ResourceNotFoundException("Habitat não encontrado com id " + dto.habitatId()));
            animal.setHabitat(habitat);
        }

        return animal;
    }
}