package com.Aula5.ProjetoZoo.ApiZoologico.repositories;

import com.Aula5.ProjetoZoo.ApiZoologico.models.Cuidador;
import com.Aula5.ProjetoZoo.ApiZoologico.models.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuidadorRepository extends JpaRepository<Cuidador, Long> {
    List<Cuidador> findByTurno(Turno turno);
    List<Cuidador> findByEspecialidade(String especialidade);
    List<Cuidador> findByEspecialidadeAndTurno(String especialidade, Turno turno);
}