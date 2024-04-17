package com.educa100.datasource.repository;

import com.educa100.datasource.entity.DocenteEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Docente SET nome = :nome, data_entrada = :data_entrada, id_usuario = :id_usuario WHERE id = :id", nativeQuery = true)
    void update(@Param("id") Long id,
                @Param("nome") String nome,
                @Param("data_entrada")Date data_entrada,
                @Param("id_usuario") Long id_usuario
    );
}
