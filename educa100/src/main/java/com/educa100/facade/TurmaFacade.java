package com.educa100.facade;

import com.educa100.controller.dto.request.TurmaRequest;
import com.educa100.datasource.entity.*;
import com.educa100.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
@Slf4j
public class TurmaFacade {

    private final TurmaServiceImpl service;

    private final DocenteServiceImpl docenteService;

    private final CursoServiceImpl cursoService;

    private final AlunoServiceImpl alunoService;

    private final NotaServiceImpl notaService;

    private final UsuarioServiceImpl usuarioService;


    public TurmaFacade(TurmaServiceImpl service, DocenteServiceImpl docenteService, CursoServiceImpl cursoService, AlunoServiceImpl alunoService, NotaServiceImpl notaService, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.docenteService = docenteService;
        this.cursoService = cursoService;
        this.alunoService = alunoService;
        this.notaService = notaService;
        this.usuarioService = usuarioService;
    }

    public TurmaEntity criarTurma(TurmaRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem criar turmas");
        }
        TurmaEntity turma = new TurmaEntity();
        boolean nomeEmUso = false;
        List<TurmaEntity> turmas = service.listarTodos();
        for (TurmaEntity t : turmas){
            if (t.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            turma.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é invalido");
        }
        List<AlunoEntity> listaGeralDeAlunos = alunoService.listarTodos();
        List<AlunoEntity> listaDeAlunos = new ArrayList<>();
        for (AlunoEntity aluno : listaGeralDeAlunos){
            if (aluno.getId_turma().getId().equals(turma.getId())){
                listaDeAlunos.add(aluno);
            }
        }
        turma.setAlunos(listaDeAlunos);
        Optional<DocenteEntity> docente = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (docente.isPresent()){
            docenteValido = docente.get();
        }else{
            log.error("Docente é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Docente é nullo");
        }
        if (usuarioLogado.getId_papel().getId() == 4 && !docenteValido.getId_usuario().equals(usuarioLogado)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Você só pode criar turmas relacionadas com você");
        }
        turma.setProfessor(docenteValido);
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }else{
            log.error("Curso é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso é nullo");
        }
        turma.setId_curso(cursoValido);
        service.salvar(turma);

        return turma;
    }

    public TurmaEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        TurmaEntity turma = service.listarPorId(id);
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        List<AlunoEntity> listaAlunos = turma.getAlunos();
        if (usuarioLogado.getId_papel().getId() == 5) {
            for (AlunoEntity aluno : listaAlunos) {
                if (aluno.getId_usuario().equals(usuarioLogado)) {
                    return turma;
                }
            }
        }
        if (usuarioLogado.getId_papel().getId() != 5){
            return turma;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED  , "Você não é autorizado a ter acesso a turma");
    }

    public TurmaEntity atualizar(Long id,TurmaRequest request,JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        TurmaEntity turma = service.listarPorId(id);
        if (!turma.getProfessor().getId_usuario().equals(usuarioLogado) &&  usuarioLogado.getId_papel().getId() != 1){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Você não é autorizado a atualizar a turma");
        }
        Optional<DocenteEntity> docente = Optional.ofNullable(docenteService.listarPorId(request.id_professor()));
        DocenteEntity docenteValido = null;
        if (docente.isPresent()){
            docenteValido = docente.get();
        }else{
            log.error("Docente é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Docente é nullo");
        }
        if (usuarioLogado.getId_papel().getId() == 4 && !docenteValido.getId_usuario().equals(usuarioLogado)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Você só pode atualizar turmas relacionadas com você");
        }
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }else{
            log.error("Curso é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso é nullo");
        }
        turma.setId_curso(cursoValido);
        turma.setProfessor(docenteValido);
        List<AlunoEntity> listaGeralDeAlunos = alunoService.listarTodos();
        List<AlunoEntity> listaDeAlunos = new ArrayList<>();
        for (AlunoEntity aluno : listaGeralDeAlunos){
            if (aluno.getId_turma().getId().equals(turma.getId())){
                listaDeAlunos.add(aluno);
            }
        }
        turma.setAlunos(listaDeAlunos);
        boolean nomeEmUso = false;
        List<TurmaEntity> turmas = service.listarTodos();
        for (TurmaEntity t : turmas){
            if (t.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (request.nome().equals(turma.getNome())){
            nomeEmUso = false;
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            turma.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é invalido");
        }
        service.atualizar(turma.getId());
        return turma;
    }

    public void deletar(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar alunos");
        }
        TurmaEntity turma = service.listarPorId(id);
        for (AlunoEntity aluno : alunoService.listarTodos()){
            if (turma.equals(aluno.getId_turma())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO, não foi possivel deletar pois existem alunos relacionadas com o turma");
            }
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<TurmaEntity> listarTodos(JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou docentes podem listar alunos");
        }
        List<TurmaEntity> listaTurmas = service.listarTodos();
        if (listaTurmas.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return listaTurmas;
    }
}
