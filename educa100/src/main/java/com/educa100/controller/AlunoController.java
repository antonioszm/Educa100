package com.educa100.controller;

import com.educa100.controller.dto.request.AlunoRequest;
import com.educa100.controller.dto.response.AlunoResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.AlunoFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
@Slf4j
public class AlunoController {

    private final AlunoFacade facade;

    public AlunoController(AlunoFacade facade) {
        this.facade = facade;
    }


    @PostMapping
    public ResponseEntity<AlunoResponse> criarAluno(@RequestBody AlunoRequest request, JwtAuthenticationToken jwt) throws Exception {
        AlunoEntity aluno = facade.criarAluno(request, jwt);
        log.info("Aluno cirado com sucesso!");
        return ResponseEntity.created(null).body(new AlunoResponse(aluno.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoEntity> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt) throws Exception {
        log.info("Aluno com {id} foi listado" + id);
        AlunoEntity aluno = facade.listarPorId(id, jwt);

        return ResponseEntity.ok(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoEntity> atualizar(@PathVariable Long id, @RequestBody AlunoRequest request, JwtAuthenticationToken jwt) throws Exception {
        AlunoEntity aluno = facade.atualizar(id, request, jwt);
        log.info("Aluno atualizado com sucesso!");
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id,JwtAuthenticationToken jwt) throws Exception {
        facade.deletar(id, jwt);
        log.info("Aluno deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<AlunoEntity>> listarTodos(JwtAuthenticationToken jwt) throws Exception {
        List<AlunoEntity> listaDeAlunos = facade.listarTodos(jwt);
        log.info("Todos os alunos listados com sucesso!");
        return ResponseEntity.ok(listaDeAlunos);
    }
    public ResponseEntity<Double> getPontuacao(Long id,JwtAuthenticationToken jwt){
        log.info("Pontuação do aluno com id " +id);
        return ResponseEntity.ok(facade.getPontuacao(id, jwt));
    }
}
