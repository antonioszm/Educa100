package com.educa100.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "docente")
public class DocenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private LocalDate data_entrada;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private UsuarioEntity id_usuario;
}
