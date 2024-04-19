package com.educa100.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "nota")
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private AlunoEntity id_aluno;

    @OneToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private DocenteEntity id_professor;

    @OneToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private MateriaEntity id_materia;

    private double valor;

    private LocalDate data;
}
