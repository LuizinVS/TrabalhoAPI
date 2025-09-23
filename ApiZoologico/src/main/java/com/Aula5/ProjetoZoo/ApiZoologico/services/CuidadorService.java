package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.CuidadorDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Turno;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.CuidadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuidadorService {
    private final CuidadorRepository cuidadorRepository;

    private CuidadorDto toDto(Cuidador c) {
        return new CuidadorDto(
                c.getId(),
                c.getNome(),
                c.getEspecialidade(),
                c.getTurno(),
                c.getEmail(),
                c.getTelefone()
        );
    }

    private Cuidador toEntity(CuidadorDto dto) {
        return Cuidador.builder()
                .id(dto.id())
                .nome(dto.nome())
                .especialidade(dto.especialidade())
                .turno(dto.turno())
                .email(dto.email())
                .telefone(dto.telefone())
                .build();
    }

    public List<CuidadorDto> listAll() {
        return cuidadorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CuidadorDto> findById(Long id) {
        return cuidadorRepository.findById(id).map(this::toDto);
    }

    public CuidadorDto create(CuidadorDto dto) {
        Cuidador c = toEntity(dto);
        Cuidador salvo = cuidadorRepository.save(c);
        return toDto(salvo);
    }

    public Optional<CuidadorDto> update(Long id, CuidadorDto dto) {
        return cuidadorRepository.findById(id)
                .map(existente -> {
                    existente.setNome(dto.nome());
                    existente.setEspecialidade(dto.especialidade());
                    existente.setTurno(dto.turno());
                    existente.setEmail(dto.email());
                    existente.setTelefone(dto.telefone());
                    Cuidador atualizado = cuidadorRepository.save(existente);
                    return toDto(atualizado);
                });
    }

    public boolean delete(Long id) {
        return cuidadorRepository.findById(id)
                .map(c -> {
                    cuidadorRepository.delete(c);
                    return true;
                })
                .orElse(false);
    }

    public List<CuidadorDto> buscarPorEspecialidade(String especialidade) {
        return cuidadorRepository.findByEspecialidade(especialidade)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CuidadorDto> buscarPorTurno(Turno turno) {
        return cuidadorRepository.findByTurno(turno)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CuidadorDto> filtrarCuidadores(String especialidade, Turno turno) {
        List<Cuidador> cuidadores;

        if (especialidade != null && turno != null) {
            cuidadores = cuidadorRepository.findByEspecialidadeAndTurno(especialidade, turno);
        } else if (especialidade != null) {
            cuidadores = cuidadorRepository.findByEspecialidade(especialidade);
        } else if (turno != null) {
            cuidadores = cuidadorRepository.findByTurno(turno);
        } else {
            cuidadores = cuidadorRepository.findAll();
        }

        return cuidadores.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}