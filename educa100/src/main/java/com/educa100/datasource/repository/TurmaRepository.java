package com.educa100.datasource.repository;

import com.educa100.datasource.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE TurmaEntity t SET t.nome = :nome, t.alunos = :alunos, t.professor = :professor, t.id_curso = :id_curso WHERE t.id = :id")
    void update(@Param("id") Long id,
                @Param("nome") String nome,
                @Param("alunos") List<AlunoEntity> alunos,
                @Param("professor") DocenteEntity professor,
                @Param("id_curso") CursoEntity id_curso
    );
}
