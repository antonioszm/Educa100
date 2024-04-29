package com.educa100.datasource.repository;

import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.NotaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE NotaEntity n SET n.id_aluno = :id_aluno, n.id_professor = :id_professor, n.id_materia = :id_materia, n.valor = :valor, n.data = :data WHERE n.id = :id")
    void update(@Param("id") Long id,
                @Param("id_aluno") AlunoEntity id_aluno,
                @Param("id_professor") DocenteEntity id_professor,
                @Param("id_materia") MateriaEntity id_materia,
                @Param("valor") double valor,
                @Param("data") LocalDate data
    );

    @Query("SELECT n FROM NotaEntity n WHERE n.id_aluno.id = :alunoId")
    List<NotaEntity> findByIdAluno(Long alunoId);
}
