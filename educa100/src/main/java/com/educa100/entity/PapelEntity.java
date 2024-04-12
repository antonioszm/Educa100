package com.educa100.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "papel")
public class PapelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String nome;
}
