package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.models.Animal;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AnimalRepository;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.AlimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AlimentacaoRepository alimentacaoRepository;

    @Autowired
    private EmailService emailService;

    // exemplo: gera relatório toda segunda às 8h
    @Scheduled(cron = "0 0 8 * * MON")
    public void enviarRelatoriosSemanais() {
        List<Animal> animais = animalRepository.findAll();

        for (Animal animal : animais) {
            var cuidador = animal.getCuidador();

            List<Alimentacao> alimentacoes = alimentacaoRepository.findByAnimalId(animal.getId());

            String corpo = """
                Relatório semanal do animal: %s

                Espécie: %s
                Idade: %d anos

                Consultas médicas: (aqui você lista se tiver tabela de consultas)
                Alimentações registradas: %d
                Última alimentação: %s

                Cuidador: %s
            """.formatted(
                    animal.getNome(),
                    animal.getEspecie(),
                    animal.getIdade(),
                    alimentacoes.size(),
                    alimentacoes.isEmpty() ? "Nenhuma" : alimentacoes.get(alimentacoes.size()-1).getTipoComida(),
                    cuidador.getNome()
            );

            emailService.sendEmail(cuidador.getEmail(), "Relatório semanal - " + animal.getNome(), corpo);

        }
    }
}
