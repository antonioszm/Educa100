package com.educa100.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "nota")
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private int id_aluno;

    @OneToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private int id_professor;

    @OneToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private int id_materia;

    private double valor;

    private Date data;
}
