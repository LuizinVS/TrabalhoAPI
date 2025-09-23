package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HistoricoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AlimentacaoRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AlimentacaoRepository alimentacaoRepository;

    public HistoricoDto gerarHistorico(Long animalId) {
        var animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        var alimentacoes = alimentacaoRepository.findByAnimalId(animalId)
                .stream()
                .map(a -> "Comida: " + a.getTipoComida() + ", Quantidade: " + a.getQuantidadeDiaria())
                .toList();

        // consultas fictícias (ajustar se já tiver tabela Consulta)
        var consultas = List.of("Exame veterinário - 2025-01-10", "Vacinação - 2025-02-02");

        return new HistoricoDto(
                animal.getId(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getIdade(),
                alimentacoes,
                consultas
        );
    }
}
