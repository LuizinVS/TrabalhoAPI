package com.Aula5.ProjetoZoo.ApiZoologico.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Alimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double quantidadeDiaria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoComida tipoComida;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(nullable = false)
    private LocalDateTime horario;

    @Column(nullable = false)
    private boolean realizada = false;

    public enum TipoComida {
        CARNE,
        FRUTAS,
        RACAO_ESPECIAL
    }
}