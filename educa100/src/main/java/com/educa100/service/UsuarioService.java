package com.educa100.service;

import com.educa100.datasource.entity.UsuarioEntity;

import java.util.Optional;

public interface UsuarioService {
    public UsuarioEntity salvar(UsuarioEntity usuario);

    public Optional<UsuarioEntity> listarPorId(Long id);
}