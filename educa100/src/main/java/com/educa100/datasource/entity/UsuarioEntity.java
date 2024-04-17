package com.educa100.datasource.entity;

import com.educa100.controller.dto.request.UsuarioRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Data
@Table(name = "usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String senha;

    @ManyToOne
    @JoinColumn(name = "id_papel", nullable = false)
    private PapelEntity id_papel;

    public boolean isLoginCorreto(UsuarioRequest usuarioRequest, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(usuarioRequest.senha(), this.senha);
    }
}
