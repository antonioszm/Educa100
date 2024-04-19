package com.educa100.datasource.repository;

import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.NotaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE MateriaEntity m SET m.nome = :nome, m.curso = :curso WHERE m.id = :id")
    void update(@Param("id") Long id,
                @Param("nome") String nome,
                 @Param("id_curso") CursoEntity id_curso
    );

    @Query("SELECT m FROM MateriaEntity m WHERE m.id_curso.id = :cursoId")
    List<MateriaEntity> findByCurso(@Param("cursoId") Long cursoId);
}
