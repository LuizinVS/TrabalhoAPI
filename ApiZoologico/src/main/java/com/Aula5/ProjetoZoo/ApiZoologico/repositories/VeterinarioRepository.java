package com.Aula5.ProjetoZoo.ApiZoologico.repositories;

import com.Aula5.ProjetoZoo.ApiZoologico.models.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario,Long> {
    List<Veterinario> findByEspecialidade(String especialidade);
}
