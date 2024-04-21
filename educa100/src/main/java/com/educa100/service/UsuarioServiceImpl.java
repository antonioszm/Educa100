package com.educa100.service;

import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.datasource.repository.UsuarioRepository;
import com.educa100.infra.exception.TurmaNotFoundException;
import com.educa100.infra.exception.UsuarioNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UsuarioEntity salvar(UsuarioEntity usuario) {
        usuario.setId(null);
        return repository.save(usuario);
    }

    @Override
    public UsuarioEntity listarPorId(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
        } catch (UsuarioNotFoundException e){
            log.error("Erro: {}",  e.getMessage());
            throw e;
        }
    }
}
