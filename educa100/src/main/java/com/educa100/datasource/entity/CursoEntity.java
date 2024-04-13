package com.educa100.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "curso")
public class CursoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nome;

    @ManyToOne
    private List<TurmaEntity> turmas;

    @ManyToOne
    private List<MateriaEntity> materias;
}
