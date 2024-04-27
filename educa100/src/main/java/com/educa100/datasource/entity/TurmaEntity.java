package com.educa100.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "turma")
public class TurmaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @OneToMany(mappedBy = "id_turma")
    private List<AlunoEntity> alunos;


    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    private DocenteEntity professor;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private CursoEntity id_curso;
}
