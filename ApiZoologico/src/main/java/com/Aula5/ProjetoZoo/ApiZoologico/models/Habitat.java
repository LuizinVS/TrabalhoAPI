package com.Aula5.ProjetoZoo.ApiZoologico.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "habitats")
@NoArgsConstructor
@AllArgsConstructor
public class Habitat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Integer capacidadeMaxima;

    public Habitat(String nome, String tipo, Integer capacidadeMaxima) {
        this.nome = nome;
        this.tipo = tipo;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }
}