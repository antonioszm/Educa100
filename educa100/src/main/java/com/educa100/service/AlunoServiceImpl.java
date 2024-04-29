package com.educa100.service;

import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.repository.AlunoRepository;
import com.educa100.infra.exception.AlunoNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AlunoServiceImpl implements AlunoService{

    private final AlunoRepository repository;

    public AlunoServiceImpl(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public AlunoEntity salvar(AlunoEntity aluno) {
        aluno.setId(null);
        return repository.save(aluno);
    }

    @Override
    public void atualizar(Long id) {
        AlunoEntity aluno = listarPorId(id);
        repository.update(aluno.getId(),aluno.getNome(), aluno.getData_nascimento(), aluno.getId_usuario(), aluno.getId_turma());
    }

    @Override
    public void removerPorId(Long id) {
        AlunoEntity aluno = listarPorId(id);
        repository.delete(aluno);
    }

    @Override
    public List<AlunoEntity> listarTodos() {
        return repository.findAll();
    }

    @Override
    public AlunoEntity listarPorId(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new AlunoNotFoundException(id));
        } catch (AlunoNotFoundException e){
            log.error("Erro: {}",  e.getMessage());
            throw e;
        }
    }
}
