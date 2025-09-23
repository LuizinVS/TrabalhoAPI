package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HabitatDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.HabitatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HabitatService {

    private final HabitatRepository repo;

    public HabitatService(HabitatRepository repo) {
        this.repo = repo;
    }

    public Habitat create(Habitat habitat) {
        return repo.save(habitat);
    }

    public List<Habitat> findAll() {
        return repo.findAll();
    }

    public Habitat findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Habitat não encontrado com id " + id
                ));
    }

    public Habitat update(Long id, Habitat habitatAtualizado) {
        Habitat existente = findById(id);
        existente.setNome(habitatAtualizado.getNome());
        existente.setTipo(habitatAtualizado.getTipo());
        existente.setCapacidadeMaxima(habitatAtualizado.getCapacidadeMaxima());
        return repo.save(existente);
    }

    public void delete(Long id) {
        Habitat existente = findById(id);
        repo.delete(existente);
    }

    public List<Habitat> findByTipo(String tipo) {
        return repo.findByTipoIgnoreCase(tipo);
    }

    public List<Habitat> findByNome(String nome) {
        return repo.findByNomeContainingIgnoreCase(nome);
    }

    public HabitatDto toDto(Habitat h) {
        return new HabitatDto(h.getId(), h.getNome(), h.getTipo(), h.getCapacidadeMaxima());
    }
}
