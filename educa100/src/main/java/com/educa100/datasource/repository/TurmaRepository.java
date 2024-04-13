package com.educa100.datasource.repository;

import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Docente SET nome = :nome, alunos = :alunos, id_professor = :professor, id_curso = :id_curso WHERE id = :id", nativeQuery = true)
    void update(@Param("id") int id,
                @Param("nome") String nome,
                @Param("alunos") List<AlunoEntity> alunos,
                @Param("professor") DocenteEntity professor,
                @Param("id_curso") int id_curso
    );
}
