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

import java.util.Date;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Docente SET nome = :nome, data_nascimento = :data_nascimento, id_usuario = :id_usuario, id_turma = :id_turma WHERE id = :id", nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("nome") String nome,
                @Param("data_nascimento")Date data_nascimento,
                @Param("id_usuario") UsuarioEntity id_usuario,
                @Param("id_turma") TurmaEntity id_turma
    );
}
