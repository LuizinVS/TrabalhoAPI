package com.Aula5.ProjetoZoo.ApiZoologico.services;

import com.Aula5.ProjetoZoo.ApiZoologico.models.Veterinario;
import com.Aula5.ProjetoZoo.ApiZoologico.repositories.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {
    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<Veterinario> findAll(){
        return veterinarioRepository.findAll();
    }

    public Veterinario create(Veterinario veterinario){
        return  veterinarioRepository.save(veterinario);
    }

    public Veterinario findById(Long id){
        return veterinarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrada"));
    }

    public Veterinario update(Long id, Veterinario veterinarioAtualizado){
        Veterinario veterinario = findById(id);

        veterinario.setNome(veterinarioAtualizado.getNome());
        return veterinarioRepository.save(veterinario);
    }

    public void delete(Long id){
        Veterinario veterinario = findById(id);
        veterinarioRepository.delete(veterinario);
    }

    public List<Veterinario> findByEspecialidade(String especialidade){
        return veterinarioRepository.findByEspecialidade(especialidade);
    }
}
