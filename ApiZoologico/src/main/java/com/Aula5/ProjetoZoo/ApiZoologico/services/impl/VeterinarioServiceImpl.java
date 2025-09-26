package com.Aula5.ProjetoZoo.ApiZoologico.services.impl;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.VeterinarioDto;
import com.Aula5.ProjetoZoo.ApiZoologico.exceptions.ResourceNotFoundException;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Veterinario;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.VeterinarioRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.services.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository repository;

    @Override
    public VeterinarioDto create(VeterinarioDto dto) {
        Veterinario v = toEntity(dto);
        return toDto(repository.save(v));
    }

    @Override
    public Optional<VeterinarioDto> update(Long id, VeterinarioDto dto) {
        Veterinario v = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id " + id));

        v.setNome(dto.nome());
        v.setIdade(dto.idade());
        v.setCRMV(dto.CRMV());
        v.setEspecialidade(dto.especialidade());

        return Optional.of(toDto(repository.save(v)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VeterinarioDto> findByIdDto(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Veterinario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Veterinario> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        Veterinario v = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado com id " + id));
        repository.delete(v);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Veterinario> findByEspecialidade(String especialidade) {
        return repository.findByEspecialidade(especialidade);
    }

    @Override
    public VeterinarioDto toDto(Veterinario veterinario) {
        return new VeterinarioDto(
                veterinario.getNome(),
                veterinario.getIdade(),
                veterinario.getCRMV(),
                veterinario.getEspecialidade()
        );
    }

    @Override
    public Veterinario toEntity(VeterinarioDto dto) {
        Veterinario v = new Veterinario();
        v.setNome(dto.nome());
        v.setIdade(dto.idade());
        v.setCRMV(dto.CRMV());
        v.setEspecialidade(dto.especialidade());
        return v;
    }
}