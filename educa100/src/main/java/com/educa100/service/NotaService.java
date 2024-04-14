package com.educa100.service;

import com.educa100.datasource.entity.NotaEntity;
import com.educa100.datasource.entity.TurmaEntity;

import java.util.List;

public interface NotaService {
    public NotaEntity salvar(NotaEntity nota);
    public void atualizar(int id);
    public void removerPorId(int id);
    public List<NotaEntity> listarPorIdAluno(int id);
    public List<NotaEntity> listarTodos();
    public NotaEntity listarPorId(int id);
}