package com.educa100.datasource.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MateriaRepository extends JpaRepository {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Docente SET nome = :nome, id_curso = :id_curso WHERE id = :id", nativeQuery = true)
    void update(@Param("id") int id,
                @Param("nome") String nome,
                 @Param("id_turma") int id_curso
    );
}
