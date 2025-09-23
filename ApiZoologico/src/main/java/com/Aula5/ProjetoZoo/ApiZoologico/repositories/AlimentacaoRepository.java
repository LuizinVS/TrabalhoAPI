package com.Aula5.ProjetoZoo.ApiZoologico.repositories;

import com.Aula5.ProjetoZoo.ApiZoologico.models.Alimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlimentacaoRepository extends JpaRepository<Alimentacao, Long> {

    List<Alimentacao> findByTipoComida(Alimentacao.TipoComida tipoComida);

    List<Alimentacao> findByAnimalId(Long animalId);

    List<Alimentacao> findByHorarioBeforeAndRealizadaFalse(LocalDateTime agora);

}
