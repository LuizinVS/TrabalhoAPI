package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.dtos.HistoricoDto;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Animal;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String destinatario, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        mailSender.send(mensagem);
    }

    public void enviarRelatorioAtualizacao(Animal animal, boolean novo) {
        String destinatario = animal.getCuidador().getEmail();
        String assunto = (novo ? "Novo animal sob seus cuidados: " : "Atualização do animal: ") + animal.getNome();

        String corpo = """
        O animal foi %s com as seguintes informações:

        Nome: %s
        Espécie: %s
        Idade: %d anos

        Cuidador Responsável: %s
        Telefone: %s
        """.formatted(
                novo ? "atribuído a você" : "atualizado",
                animal.getNome(),
                animal.getEspecie(),
                animal.getIdade(),
                animal.getCuidador().getNome(),
                animal.getCuidador().getTelefone()
        );

        sendEmail(destinatario, assunto, corpo);
    }

    public void enviarHistorico(Animal animal, HistoricoDto historico) {
        String destinatario = animal.getCuidador().getEmail();
        String assunto = "Histórico do novo animal sob seus cuidados: " + animal.getNome();

        String corpo = """
        Você agora é responsável pelo animal:

        Nome: %s
        Espécie: %s
        Idade: %d anos

        Histórico de alimentações:
        %s

        Histórico de consultas:
        %s
        """.formatted(
                historico.nome(),
                historico.especie(),
                historico.idade(),
                String.join("\n", historico.alimentacoes()),
                String.join("\n", historico.consultas())
        );

        sendEmail(destinatario, assunto, corpo);
    }

    public void enviarAvisoTrocaCuidador(Animal animal, Cuidador antigoCuidador) {
        String assunto = "Você não é mais responsável pelo animal " + animal.getNome();
        String corpo = """
        Olá %s,

        Informamos que você não é mais o cuidador responsável pelo animal:

        Nome: %s
        Espécie: %s
        Idade: %d anos

        Novo cuidador: %s

        """.formatted(
                antigoCuidador.getNome(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getIdade(),
                animal.getCuidador() != null ? animal.getCuidador().getNome() : "Ainda não definido"
        );

        sendEmail(antigoCuidador.getEmail(), assunto, corpo);
    }
}