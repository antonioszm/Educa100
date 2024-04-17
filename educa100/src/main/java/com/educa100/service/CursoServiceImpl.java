package com.educa100.service;

import com.educa100.datasource.entity.CursoEntity;
import com.educa100.datasource.repository.CursoRepository;
import com.educa100.infra.exception.CursoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        repository.update(curso.getId(),curso.getNome(),curso.getTurmas(), curso.getMaterias());
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
        return repository.findById(id).orElseThrow(() -> new CursoNotFoundException(id));
    }
}
