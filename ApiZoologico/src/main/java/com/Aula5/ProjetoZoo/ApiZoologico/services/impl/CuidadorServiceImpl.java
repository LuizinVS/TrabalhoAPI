package com.Aula5.ProjetoZoo.ApiZoologico.services.impl;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.CuidadorDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Turno;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.CuidadorRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.services.CuidadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CuidadorServiceImpl implements CuidadorService {

    private final CuidadorRepository repository;

    @Override
    public CuidadorDto create(CuidadorDto dto) {
        Cuidador cuidador = toEntity(dto);
        return toDto(repository.save(cuidador));
    }

    @Override
    public Optional<CuidadorDto> update(Long id, CuidadorDto dto) {
        Cuidador cuidador = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuidador não encontrado com id " + id));

        cuidador.setNome(dto.nome());
        cuidador.setEspecialidade(dto.especialidade());
        cuidador.setTurno(dto.turno());
        cuidador.setEmail(dto.email());
        cuidador.setTelefone(dto.telefone());

        return Optional.of(toDto(repository.save(cuidador)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CuidadorDto> findById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuidadorDto> listAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Cuidador cuidador = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuidador não encontrado com id " + id));
        repository.delete(cuidador);
    }

    @Override
    public List<CuidadorDto> filtrarCuidadores(String especialidade, Turno turno) {
        List<Cuidador> cuidadores;

        if(especialidade != null && turno != null) {
            cuidadores = repository.findByEspecialidadeAndTurno(especialidade, turno);
        } else if(especialidade != null) {
            cuidadores = repository.findByEspecialidade(especialidade);
        } else if(turno != null) {
            cuidadores = repository.findByTurno(turno);
        } else {
            cuidadores = repository.findAll();
        }

        return cuidadores.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CuidadorDto toDto(Cuidador cuidador) {
        return new CuidadorDto(
                cuidador.getId(),
                cuidador.getNome(),
                cuidador.getEspecialidade(),
                cuidador.getTurno(),
                cuidador.getEmail(),
                cuidador.getTelefone()
        );
    }

    @Override
    public Cuidador toEntity(CuidadorDto dto) {
        Cuidador cuidador = new Cuidador();
        cuidador.setNome(dto.nome());
        cuidador.setEspecialidade(dto.especialidade());
        cuidador.setTurno(dto.turno());
        cuidador.setEmail(dto.email());
        cuidador.setTelefone(dto.telefone());
        return cuidador;
    }
}