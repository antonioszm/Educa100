package com.educa100.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "turma")
public class TurmaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nome;

    @ManyToOne
    private List<AlunoEntity> alunos;


    @OneToMany
    @JoinColumn(name = "id_professor", nullable = false)
    private DocenteEntity professor;

    @OneToMany
    @JoinColumn(name = "id_curso", nullable = false)
    private int id_curso;
}
