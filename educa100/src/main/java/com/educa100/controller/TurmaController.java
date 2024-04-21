package com.educa100.controller;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.AlunoResponse;
import com.educa100.controller.dto.response.TurmaResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.TurmaFacade;
import com.educa100.infra.exception.AlunoNotFoundException;
import com.educa100.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
@Slf4j
public class TurmaController {

    private final TurmaFacade facade;

    public TurmaController(TurmaFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<TurmaResponse> criarTurma(@RequestBody TurmaRequest request, JwtAuthenticationToken jwt){
        TurmaEntity turma = facade.criarTurma(request, jwt);
        log.info("Turma cirado com sucesso!");
        return ResponseEntity.created(null).body(new TurmaResponse(turma.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaEntity> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt){
        log.info("Turma com {id} foi listado" + id);
        TurmaEntity turma = facade.listarPorId(id, jwt);

        return ResponseEntity.ok(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaEntity> atualizar(@PathVariable Long id, @RequestBody TurmaRequest request, JwtAuthenticationToken jwt){
        TurmaEntity turma = facade.atualizar(id, request, jwt);
        log.info("Turma atualizado com sucesso!");
        return ResponseEntity.ok(turma);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id, JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Turma deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<TurmaEntity>> listarTodos(JwtAuthenticationToken jwt){
        List<TurmaEntity> listaDeTurmas = facade.listarTodos(jwt);
        log.info("Todos os alunos listados com sucesso!");
        return ResponseEntity.ok(listaDeTurmas);
    }
}
