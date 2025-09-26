package com.Aula5.ProjetoZoo.ApiZoologico.services.impl;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AlimentacaoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Animal;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AlimentacaoRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AnimalRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.services.AlimentacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlimentacaoServiceImpl implements AlimentacaoService {

    private final AlimentacaoRepository repository;
    private final AnimalRepository animalRepository;

    @Override
    public AlimentacaoDto create(AlimentacaoDto dto) {
        Alimentacao entity = toEntity(dto);
        Alimentacao salvo = repository.save(entity);
        return toDto(salvo);
    }

    @Override
    public Optional<AlimentacaoDto> update(Long id, AlimentacaoDto dto) {
        Alimentacao alimentacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimentação não encontrada com id " + id));

        alimentacao.setQuantidadeDiaria(dto.quantidadeDiaria());
        alimentacao.setTipoComida(dto.tipoComida());
        alimentacao.setHorario(dto.horario());
        alimentacao.setRealizada(dto.realizada());

        if(dto.animalId() != null){
            Animal animal = animalRepository.findById(dto.animalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado com id " + dto.animalId()));
            alimentacao.setAnimal(animal);
        }

        Alimentacao atualizado = repository.save(alimentacao);
        return Optional.of(toDto(atualizado));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AlimentacaoDto> findById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlimentacaoDto> listAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Alimentacao alimentacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alimentação não encontrada com id " + id));
        repository.delete(alimentacao);
    }

    @Override
    public List<AlimentacaoDto> findByTipoComida(String tipo) {
        Alimentacao.TipoComida tipoEnum;
        try {
            tipoEnum = Alimentacao.TipoComida.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de comida inválido: " + tipo);
        }
        return repository.findByTipoComida(tipoEnum).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AlimentacaoDto> findByAnimalId(Long animalId) {
        return repository.findByAnimalId(animalId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public AlimentacaoDto toDto(Alimentacao alimentacao) {
        return new AlimentacaoDto(
                alimentacao.getId(),
                alimentacao.getQuantidadeDiaria(),
                alimentacao.getTipoComida(),
                alimentacao.getHorario(),
                alimentacao.isRealizada(),
                alimentacao.getAnimal() != null ? alimentacao.getAnimal().getId() : null,
                alimentacao.getAnimal() != null ? alimentacao.getAnimal().getNome() : null,
                null
        );
    }

    @Override
    public Alimentacao toEntity(AlimentacaoDto dto) {
        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setQuantidadeDiaria(dto.quantidadeDiaria());
        alimentacao.setTipoComida(dto.tipoComida());
        alimentacao.setHorario(dto.horario());
        alimentacao.setRealizada(dto.realizada());

        if(dto.animalId() != null){
            Animal animal = animalRepository.findById(dto.animalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado com id " + dto.animalId()));
            alimentacao.setAnimal(animal);
        }

        return alimentacao;
    }
}