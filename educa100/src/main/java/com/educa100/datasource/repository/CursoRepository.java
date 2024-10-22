package com.educa100.datasource.repository;

import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE CursoEntity c SET c.nome = :nome WHERE c.id = :id")
    void update(@Param("id") Long id,
                @Param("nome") String nome
    );
}
