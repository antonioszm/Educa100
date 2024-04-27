package com.educa100.service;

import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.repository.CursoRepository;
import com.educa100.infra.exception.CursoNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CursoServiceImpl implements CursoService{

    private final CursoRepository repository;

    public CursoServiceImpl(CursoRepository repository) {
        this.repository = repository;
    }

    @Override
    public CursoEntity salvar(CursoEntity curso) {
        curso.setId(null);
        return repository.save(curso);
    }

    @Override
    public void atualizar(Long id) {
        CursoEntity curso = listarPorId(id);
        repository.update(curso.getId(),curso.getNome());
    }

    @Override
    public void removerPorId(Long id) {
        CursoEntity curso = listarPorId(id);
        repository.delete(curso);
    }

    @Override
    public List<CursoEntity> listarTodos() {
        return repository.findAll();
    }

    @Override
    public CursoEntity listarPorId(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new CursoNotFoundException(id));
        } catch (CursoNotFoundException e){
            log.error("Erro: {}",  e.getMessage());
            throw e;
        }

    }
}
