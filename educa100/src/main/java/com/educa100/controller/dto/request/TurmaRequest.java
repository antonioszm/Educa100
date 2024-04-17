package com.educa100.controller.dto.request;

import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.MateriaEntity;
import com.educa100.datasource.entity.TurmaEntity;

import java.util.List;

public record TurmaRequest(String nome, List<AlunoEntity> alunos, DocenteEntity professor, Long id_curso) {
}
