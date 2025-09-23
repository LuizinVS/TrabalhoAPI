package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AnimalDto;
import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HistoricoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Animal;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AnimalRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.CuidadorRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.HabitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final HabitatRepository habitatRepository;
    private final CuidadorRepository cuidadorRepository;
    private final EmailService emailService;
    private final HistoricoService historicoService;

    public AnimalService(AnimalRepository animalRepository, HabitatRepository habitatRepository,
                         CuidadorRepository cuidadorRepository, EmailService emailService,
                         HistoricoService historicoService) {
        this.animalRepository = animalRepository;
        this.habitatRepository = habitatRepository;
        this.cuidadorRepository = cuidadorRepository;
        this.emailService = emailService;
        this.historicoService = historicoService;
    }

    // ---------- ENTITY ----------
    public List<Animal> findAllEntities() {
        return animalRepository.findAll();
    }

    public Animal findByIdEntity(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public Animal createEntity(AnimalDto dto) {
        Habitat habitat = habitatRepository.findById(dto.habitatId())
                .orElseThrow(() -> new RuntimeException("Habitat não encontrado"));

        long quantidadeAnimais = animalRepository.countByHabitatId(habitat.getId());
        if (quantidadeAnimais >= habitat.getCapacidadeMaxima()) {
            throw new RuntimeException("Capacidade máxima do habitat atingida");
        }

        Cuidador cuidador = cuidadorRepository.findById(dto.cuidadorId())
                .orElseThrow(() -> new RuntimeException("Cuidador do animal não encontrado"));

        Animal animal = new Animal();
        animal.setNome(dto.nome());
        animal.setIdade(dto.idade());
        animal.setEspecie(dto.especie());
        animal.setCuidador(cuidador);
        animal.setHabitat(habitat);

        Animal salvo = animalRepository.save(animal);

        emailService.enviarRelatorioAtualizacao(salvo, true);
        HistoricoDto historico = historicoService.gerarHistorico(salvo.getId());
        emailService.enviarHistorico(salvo, historico);

        return salvo;
    }

    public Animal updateEntity(Long id, AnimalDto dto) {
        Animal existente = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        Cuidador cuidadorAnterior = existente.getCuidador();

        existente.setNome(dto.nome());
        existente.setEspecie(dto.especie());
        existente.setIdade(dto.idade());
        existente.setHabitat(dto.habitatId() != null ? habitatRepository.findById(dto.habitatId()).orElse(null) : null);
        existente.setCuidador(dto.cuidadorId() != null ? cuidadorRepository.findById(dto.cuidadorId()).orElse(null) : null);

        Animal atualizado = animalRepository.save(existente);

        if (atualizado.getCuidador() != null && !atualizado.getCuidador().equals(cuidadorAnterior)) {
            if (cuidadorAnterior != null) {
                emailService.enviarAvisoTrocaCuidador(atualizado, cuidadorAnterior);
            }

            emailService.enviarRelatorioAtualizacao(atualizado, false);
            HistoricoDto historico = historicoService.gerarHistorico(atualizado.getId());
            emailService.enviarHistorico(atualizado, historico);
        }

        return atualizado;
    }

    // ---------- CONSULTAS ----------
    public List<Animal> findByEspecieEntity(String especie) {
        return animalRepository.findByEspecie(especie);
    }

    public List<Animal> findByIdadeBetweenEntity(int idadeMin, int idadeMax) {
        return animalRepository.findByIdadeBetween(idadeMin, idadeMax);
    }

    public List<Animal> findByNomeEntity(String nome) {
        return animalRepository.findByNomeContainingIgnoreCase(nome);
    }

    // ---------- DELETE ----------
    public void delete(Long id) {
        Animal existente = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
        animalRepository.delete(existente);
    }

}
