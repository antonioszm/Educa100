package com.educa100.datasource.repository;

import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.PapelEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PapelRepository extends JpaRepository<PapelEntity, Integer> {
}
