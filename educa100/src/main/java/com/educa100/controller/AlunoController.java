package com.educa100.controller;

import com.educa100.controller.dto.request.AlunoRequest;
import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.controller.dto.response.AlunoResponse;
import com.educa100.controller.dto.response.TurmaResponse;
import com.educa100.datasource.entity.*;
import com.educa100.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoServiceImpl service;

    private final TurmaServiceImpl turmaService;

    private final UsuarioServiceImpl usuarioService;

    public AlunoController(AlunoServiceImpl service, TurmaServiceImpl turmaService, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.turmaService = turmaService;
        this.usuarioService = usuarioService;
    }


    @PostMapping
    public ResponseEntity<AlunoResponse> criarAluno(@RequestBody AlunoRequest request){

        AlunoEntity aluno = new AlunoEntity();
        aluno.setNome(request.nome());
        aluno.setData_nascimento(request.dataNascimento());
        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        }
        aluno.setId_usuario(usuarioValido);
        Optional<TurmaEntity> turma = Optional.ofNullable(turmaService.listarPorId(request.id_turma()));
        TurmaEntity turmaValida = null;
        if (turma.isPresent()){
            turmaValida = turma.get();
        }
        aluno.setId_turma(turmaValida);
        service.salvar(aluno);

        return ResponseEntity.created(null).body(new AlunoResponse(aluno.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoEntity> listarPorId(@PathVariable Long id){
        AlunoEntity aluno = service.listarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoEntity> atualizar(@PathVariable Long id, @RequestBody AlunoRequest request){
        AlunoEntity aluno = service.listarPorId(id);
        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        }
        Optional<TurmaEntity> turma = Optional.ofNullable(turmaService.listarPorId(request.id_turma()));
        TurmaEntity turmaValida = null;
        if (turma.isPresent()){
            turmaValida = turma.get();
        }
        aluno.setNome(request.nome());
        aluno.setData_nascimento(request.dataNascimento());
        aluno.setId_usuario(usuarioValido);
        aluno.setId_turma(turmaValida);
        service.atualizar(aluno.getId());
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<AlunoEntity>> listarTodos(){
        List<AlunoEntity> listaAlunos = service.listarTodos();
        if (listaAlunos.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listaAlunos);
    }
}
