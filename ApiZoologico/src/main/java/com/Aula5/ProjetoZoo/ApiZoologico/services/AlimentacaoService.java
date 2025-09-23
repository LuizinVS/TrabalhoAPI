package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.AlimentacaoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Animal;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AlimentacaoRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlimentacaoService {

    private final AlimentacaoRepository alimentacaoRepository;
    private final AnimalRepository animalRepository;
    private final EmailService emailService;

    public AlimentacaoService(AlimentacaoRepository alimentacaoRepository,
                              AnimalRepository animalRepository,
                              EmailService emailService) {
        this.alimentacaoRepository = alimentacaoRepository;
        this.animalRepository = animalRepository;
        this.emailService = emailService;
    }

    // ========= Conversões DTO =========
    private AlimentacaoDto toDto(Alimentacao alimentacao) {
        Animal animal = alimentacao.getAnimal();
        return new AlimentacaoDto(
                alimentacao.getId(),
                alimentacao.getTipoComida(),
                alimentacao.getQuantidadeDiaria(),
                alimentacao.getHorario(),
                alimentacao.isRealizada(),
                animal != null ? animal.getId() : null,
                animal != null ? animal.getNome() : null,
                (animal != null && animal.getCuidador() != null) ? animal.getCuidador().getNome() : null
        );
    }

    private Alimentacao toEntity(AlimentacaoDto dto) {
        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setId(dto.id());
        alimentacao.setTipoComida(dto.tipoComida());
        alimentacao.setQuantidadeDiaria(dto.quantidadeDiaria());
        alimentacao.setHorario(dto.horario());
        alimentacao.setRealizada(dto.realizada());

        if (dto.animalId() != null) {
            Animal animal = animalRepository.findById(dto.animalId())
                    .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
            alimentacao.setAnimal(animal);
        }
        return alimentacao;
    }

    // ========= CRUD =========
    public List<AlimentacaoDto> findAllDto() {
        return alimentacaoRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public AlimentacaoDto create(AlimentacaoDto alimentacaoDto) {
        Alimentacao entity = toEntity(alimentacaoDto);
        Alimentacao salvo = alimentacaoRepository.save(entity);

//        emailService.enviarAviso(salvo);

        return toDto(salvo);
    }

    public AlimentacaoDto findDtoById(Long id) {
        return alimentacaoRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Alimentação não encontrada"));
    }

    public AlimentacaoDto update(Long id, AlimentacaoDto dto) {
        Alimentacao existente = alimentacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimentação não encontrada"));

        existente.setTipoComida(dto.tipoComida());
        existente.setQuantidadeDiaria(dto.quantidadeDiaria());
        existente.setHorario(dto.horario());
        existente.setRealizada(dto.realizada());

        if (dto.animalId() != null) {
            Animal animal = animalRepository.findById(dto.animalId())
                    .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
            existente.setAnimal(animal);
        } else {
            existente.setAnimal(null);
        }

        Alimentacao atualizado = alimentacaoRepository.save(existente);
        return toDto(atualizado);
    }

    public void delete(Long id) {
        Alimentacao existente = alimentacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alimentação não encontrada"));
        alimentacaoRepository.delete(existente);
    }

    public List<AlimentacaoDto> findDtoByTipoComida(String tipoComidaStr) {
        try {
            Alimentacao.TipoComida tipo = Alimentacao.TipoComida.valueOf(tipoComidaStr.toUpperCase());
            return alimentacaoRepository.findByTipoComida(tipo)
                    .stream()
                    .map(this::toDto)
                    .toList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    public List<AlimentacaoDto> findDtoByAnimalId(Long animalId) {
        return alimentacaoRepository.findByAnimalId(animalId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // ========= Rotina Automática =========
    @Scheduled(cron = "0 * * * * *") // a cada minuto
    public void verificarAlimentacoesPendentes() {
        LocalDateTime agora = LocalDateTime.now();
        List<Alimentacao> pendentes = alimentacaoRepository.findByHorarioBeforeAndRealizadaFalse(agora);

        for (Alimentacao alimentacao : pendentes) {
//            emailService.enviarAviso(alimentacao);
            alimentacao.setRealizada(true);
            alimentacaoRepository.save(alimentacao);
        }
    }
}
