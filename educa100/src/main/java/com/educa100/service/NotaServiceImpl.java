package com.educa100.service;

import com.educa100.datasource.entity.AlunoEntity;
import com.educa100.datasource.entity.NotaEntity;
import com.educa100.datasource.entity.TurmaEntity;
import com.educa100.datasource.repository.NotaRepository;
import com.educa100.infra.exception.NotaNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotaServiceImpl implements NotaService{

    private final NotaRepository repository;

    public NotaServiceImpl(NotaRepository repository, AlunoService alunoService) {
        this.repository = repository;
    }

    @Override
    public NotaEntity salvar(NotaEntity nota) {
        nota.setId(null);
        return repository.save(nota);
    }

    @Override
    public void atualizar(Long id) {
        NotaEntity nota = listarPorId(id);
        repository.update(nota.getId(),nota.getId_aluno(), nota.getId_professor(), nota.getId_materia(),nota.getValor(), nota.getData());
    }

    @Override
    public void removerPorId(Long id) {
        NotaEntity nota = listarPorId(id);
        repository.delete(nota);
    }
    @Override
    public List<NotaEntity> listarTodos() {
        return repository.findAll();
    }

    @Override
    public List<NotaEntity> listarPorIdAluno(Long id) {
      return repository.findByIdAluno(id);
    }

    @Override
    public NotaEntity listarPorId(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new NotaNotFoundException(id));
        } catch (NotaNotFoundException e){
            log.error("Erro: {}",  e.getMessage());
            throw e;
        }
    }
}
