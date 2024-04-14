package com.educa100.datasource.entity;

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


    public enum Papel{
        ADM(1,"adm"),
        PEDAGOGICO(2,"pedagogico"),
        RECRUITER(3,"recruiter"),
        PROFESSOR(4,"professor"),
        ALUNO(5,"aluno");

        Papel(int i, String s) {

        }
    }
}
