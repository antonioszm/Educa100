package com.educa100.service;

import com.educa100.datasource.entity.NotaEntity;

import java.util.List;

public interface NotaService {
    public NotaEntity salvar(NotaEntity nota);
    public void atualizar(NotaEntity nota);
    public void removerPorId(int id);
    public List<NotaEntity> listarTodos();
    public NotaEntity listarPorId(int id);
}