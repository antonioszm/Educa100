package com.educa100.controller.dto.request;

import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.TurmaEntity;

import java.util.Date;
import java.util.List;

public record CursoRequest(String nome, List<TurmaEntity> turmas, List<MateriaEntity> materias) {
}
