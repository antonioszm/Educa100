package com.educa100.service;

import com.educa100.datasource.entity.NotaEntity;
import com.educa100.datasource.entity.TurmaEntity;

import java.util.List;

public interface NotaService {
    public NotaEntity salvar(NotaEntity nota);
    public void atualizar(Long id);
    public void removerPorId(Long id);
    public List<NotaEntity> listarPorIdAluno(Long id);
    public List<NotaEntity> listarTodos();
    public NotaEntity listarPorId(Long id);
}