package com.educa100.datasource.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AlunoRepository extends JpaRepository {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Docente SET nome = :nome, data_nascimento = :data_nascimento, id_usuario = :id_usuario, id_turma = :id_turma WHERE id = :id", nativeQuery = true)
    void update(@Param("id") int id,
                @Param("nome") String nome,
                @Param("data_nascimento")Date data_nascimento,
                @Param("id_usuario") int id_usuario,
                @Param("id_turma") int id_turma
    );
}
