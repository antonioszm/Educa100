package com.educa100.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "materia")
public class MateriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private Long id_curso;
}
