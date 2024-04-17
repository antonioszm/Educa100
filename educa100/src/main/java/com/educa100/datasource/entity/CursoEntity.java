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
    private Long id;

    @Column(unique = true)
    private String nome;

    @OneToMany
    @JoinColumn(name = "id_turmas")
    private List<TurmaEntity> turmas;

    @OneToMany
    @JoinColumn(name = "id_materias")
    private List<MateriaEntity> materias;
}
