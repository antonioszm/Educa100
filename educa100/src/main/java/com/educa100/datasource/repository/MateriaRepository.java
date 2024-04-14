package com.educa100.datasource.repository;

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
public interface MateriaRepository extends JpaRepository<MateriaEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Docente SET nome = :nome, id_curso = :id_curso WHERE id = :id", nativeQuery = true)
    void update(@Param("id") int id,
                @Param("nome") String nome,
                 @Param("id_curso") int id_curso
    );

    List<MateriaEntity> findByIdCurso(int id);
}
