package com.educa100.controller;

import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.TurmaResponse;
import com.educa100.controller.dto.response.creation.TurmaCreationResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.TurmaFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/turmas")
@Slf4j
public class TurmaController {

    private final TurmaFacade facade;

    public TurmaController(TurmaFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<TurmaCreationResponse> criarTurma(@RequestBody TurmaRequest request, JwtAuthenticationToken jwt){
        TurmaEntity turma = facade.criarTurma(request, jwt);
        log.info("Turma cirado com sucesso!");
        return ResponseEntity.created(null).body(new TurmaCreationResponse(turma.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponse> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt){
        log.info("Turma com {id} foi listado" + id);
        TurmaEntity turma = facade.listarPorId(id, jwt);
        List<AlunoEntity> listaDeAlunos = turma.getAlunos();
        List<Long> alunosId = new ArrayList<>();
        for (AlunoEntity aluno :listaDeAlunos){
            alunosId.add(aluno.getId());
        }
        return ResponseEntity.ok(new TurmaResponse(turma.getId(),turma.getNome(),alunosId,turma.getProfessor().getId(), turma.getId_curso().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponse> atualizar(@PathVariable Long id, @RequestBody TurmaRequest request, JwtAuthenticationToken jwt){
        TurmaEntity turma = facade.atualizar(id, request, jwt);
        log.info("Turma atualizado com sucesso!");
        List<AlunoEntity> listaDeAlunos = turma.getAlunos();
        List<Long> alunosId = new ArrayList<>();
        for (AlunoEntity aluno :listaDeAlunos){
            alunosId.add(aluno.getId());
        }
        return ResponseEntity.ok(new TurmaResponse(turma.getId(),turma.getNome(),alunosId,turma.getProfessor().getId(), turma.getId_curso().getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id, JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Turma deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TurmaResponse>> listarTodos(JwtAuthenticationToken jwt){
        List<TurmaEntity> listaDeTurmas = facade.listarTodos(jwt);
        List<TurmaResponse> listaDeTurmasDto = new ArrayList<>();
        log.info("Todos os alunos listados com sucesso!");
        for (TurmaEntity turma : listaDeTurmas){
            List<AlunoEntity> listaDeAlunos = turma.getAlunos();
            List<Long> alunosId = new ArrayList<>();
            for (AlunoEntity aluno :listaDeAlunos){
                alunosId.add(aluno.getId());
            }
            TurmaResponse dto = new TurmaResponse(turma.getId(),turma.getNome(),alunosId,turma.getProfessor().getId(), turma.getId_curso().getId());
            listaDeTurmasDto.add(dto);
        }
        return ResponseEntity.ok(listaDeTurmasDto);
    }
}
