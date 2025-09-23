package com.Aula5.ProjetoZoo.ApiZoologico.repositories;


import com.Aula5.ProjetoZoo.ApiZoologico.models.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {
    List<Habitat> findByTipoIgnoreCase(String tipo);
    List<Habitat> findByNomeContainingIgnoreCase(String nome);
}
