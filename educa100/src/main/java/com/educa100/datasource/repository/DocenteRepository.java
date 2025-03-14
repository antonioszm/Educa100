package com.educa100.datasource.repository;

import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE DocenteEntity d SET d.nome = :nome, d.data_entrada = :data_entrada, d.id_usuario = :id_usuario WHERE d.id = :id")
    void update(@Param("id") Long id,
                @Param("nome") String nome,
                @Param("data_entrada") LocalDate data_entrada,
                @Param("id_usuario") UsuarioEntity id_usuario
    );
}
