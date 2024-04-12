package com.educa100.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Data
@Table(name = "aluno")
public class AlunoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nome;

    private Date data_nascimento;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private int id_usuario;

    @OneToMany
    @JoinColumn(name = "id_turma", nullable = false)
    private int id_turma;
}
