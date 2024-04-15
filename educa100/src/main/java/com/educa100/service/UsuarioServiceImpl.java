package com.educa100.service;

import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.datasource.repository.UsuarioRepository;
import com.educa100.infra.exception.UsuarioNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UsuarioEntity salvar(UsuarioEntity usuario) {
        usuario.setId(0);
        return repository.save(usuario);
    }
}
