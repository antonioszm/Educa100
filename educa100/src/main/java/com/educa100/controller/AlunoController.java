package com.educa100.controller;

import com.educa100.controller.dto.request.AlunoRequest;
import com.educa100.controller.dto.response.*;
import com.educa100.controller.dto.response.creation.AlunoCreationResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.AlunoFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alunos")
@Slf4j
public class AlunoController {

    private final AlunoFacade facade;

    public AlunoController(AlunoFacade facade) {
        this.facade = facade;
    }


    @PostMapping
    public ResponseEntity<AlunoCreationResponse> criarAluno(@RequestBody AlunoRequest request, JwtAuthenticationToken jwt) throws Exception {
        AlunoEntity aluno = facade.criarAluno(request, jwt);
        log.info("Aluno cirado com sucesso!");
        return ResponseEntity.created(null).body(new AlunoCreationResponse(aluno.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt) throws Exception {
        log.info("Aluno com {id} foi listado" + id);
        AlunoEntity aluno = facade.listarPorId(id, jwt);

        return ResponseEntity.ok(new AlunoResponse(aluno.getId(),aluno.getNome(),aluno.getData_nascimento(),aluno.getId_usuario().getId(),aluno.getId_turma().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> atualizar(@PathVariable Long id, @RequestBody AlunoRequest request, JwtAuthenticationToken jwt) throws Exception {
        AlunoEntity aluno = facade.atualizar(id, request, jwt);
        log.info("Aluno atualizado com sucesso!");
        return ResponseEntity.ok(new AlunoResponse(aluno.getId(),aluno.getNome(),aluno.getData_nascimento(),aluno.getId_usuario().getId(),aluno.getId_turma().getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id,JwtAuthenticationToken jwt) throws Exception {
        facade.deletar(id, jwt);
        log.info("Aluno deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponse>> listarTodos(JwtAuthenticationToken jwt) throws Exception {
        List<AlunoEntity> listaDeAlunos = facade.listarTodos(jwt);
        List<AlunoResponse> listaDeAlunoDto = new ArrayList<>();
        for (AlunoEntity aluno : listaDeAlunos){
            AlunoResponse dto = new AlunoResponse(aluno.getId(),aluno.getNome(),aluno.getData_nascimento(),aluno.getId_usuario().getId(),aluno.getId_turma().getId());
            listaDeAlunoDto.add(dto);
        }
        log.info("Todos os alunos listados com sucesso!");
        return ResponseEntity.ok(listaDeAlunoDto);
    }
    @GetMapping("/{id}/pontuacao")
    public ResponseEntity<Double> getPontuacao(@PathVariable Long id,JwtAuthenticationToken jwt){
        log.info("Pontuação do aluno com id " +id);
        return ResponseEntity.ok(facade.getPontuacao(id, jwt));
    }

    @GetMapping("/{id_aluno}/notas")
    public ResponseEntity<List<NotaResponse>> listarNotasPorAluno(@PathVariable Long id_aluno,JwtAuthenticationToken jwt){
        List<NotaEntity> listaNotas = facade.listarNotasPorAluno(id_aluno,jwt);
        List<NotaResponse> listaNotasDto = new ArrayList<>();
        for (NotaEntity nota: listaNotas){
            NotaResponse dto = new NotaResponse(nota.getId(),nota.getId_aluno().getId(),nota.getId_professor().getId(),nota.getId_materia().getId(),nota.getValor(),nota.getData());
            listaNotasDto.add(dto);
        }
        log.info("Notas do aluno com id " +id_aluno);
        return ResponseEntity.ok(listaNotasDto);
    }
}
