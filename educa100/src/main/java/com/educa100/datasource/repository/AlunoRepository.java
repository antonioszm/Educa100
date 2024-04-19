package com.educa100.datasource.repository;

import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.TurmaEntity;
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
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE AlunoEntity a SET a.nome = :nome, a.dataNascimento = :dataNascimento, a.usuario = :usuario, a.turma = :turma WHERE a.id = :id")
    void update(@Param("id") Long id,
                @Param("nome") String nome,
                @Param("data_nascimento") LocalDate data_nascimento,
                @Param("id_usuario") UsuarioEntity id_usuario,
                @Param("id_turma") TurmaEntity id_turma
    );
}
