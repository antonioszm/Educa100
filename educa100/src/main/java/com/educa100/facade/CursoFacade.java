package com.educa100.facade;

import com.educa100.controller.dto.request.CursoRequest;
import com.educa100.datasource.entity.*;
import com.educa100.service.CursoServiceImpl;
import com.educa100.service.MateriaServiceImpl;
import com.educa100.service.TurmaServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CursoFacade {

    private final CursoServiceImpl service;

    private final TurmaServiceImpl turmaService;

    private final MateriaServiceImpl materiaService;
    private final UsuarioServiceImpl usuarioService;

    public CursoFacade(CursoServiceImpl service, TurmaServiceImpl turmaService, MateriaServiceImpl materiaService, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.turmaService = turmaService;
        this.materiaService = materiaService;
        this.usuarioService = usuarioService;
    }
    public CursoEntity criarCursos(CursoRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem cirar cursos");
        }
        CursoEntity curso = new CursoEntity();
        boolean nomeEmUso = false;
        List<CursoEntity> listaDeDocentes = service.listarTodos();
        for (CursoEntity cursos : listaDeDocentes){
            if (cursos.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            curso.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome invalido");
        }
        List<TurmaEntity> listaGeralDeTurmas = turmaService.listarTodos();
        List<TurmaEntity> listaDeTurmas = new ArrayList<>();
        for (TurmaEntity turma : listaGeralDeTurmas){
            if (turma.getId_curso().getId().equals(curso.getId())){
                listaDeTurmas.add(turma);
            }
        }
        curso.setTurmas(listaDeTurmas);
        List<MateriaEntity> listaGeralDeMaterias = materiaService.listarTodos();
        List<MateriaEntity> listaDeMaterias = new ArrayList<>();
        for (MateriaEntity materia : listaGeralDeMaterias){
            if (materia.getId_curso().getId().equals(curso.getId())){
                listaDeMaterias.add(materia);
            }
        }
        curso.setMaterias(listaDeMaterias);
        service.salvar(curso);
        return curso;
    }

    public CursoEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou  Docentes podem listar cursos");
        }
        CursoEntity curso = service.listarPorId(id);
        return curso;
    }

    public CursoEntity atualizar(Long id, CursoRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou  Docentes podem listar cursos");
        }
        CursoEntity curso = service.listarPorId(id);
        boolean nomeEmUso = false;
        List<CursoEntity> cursos = service.listarTodos();
        for (CursoEntity c : cursos){
            if (c.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (request.nome().equals(curso.getNome())){
            nomeEmUso = false;
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            curso.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome invalido");
        }
        List<TurmaEntity> listaGeralDeTurmas = turmaService.listarTodos();
        List<TurmaEntity> listaDeTurmas = new ArrayList<>();
        for (TurmaEntity turma : listaGeralDeTurmas){
            if (turma.getId_curso().getId().equals(curso.getId())){
                listaDeTurmas.add(turma);
            }
        }
        curso.setTurmas(listaDeTurmas);
        List<MateriaEntity> listaGeralDeMaterias = materiaService.listarTodos();
        List<MateriaEntity> listaDeMaterias = new ArrayList<>();
        for (MateriaEntity materia : listaGeralDeMaterias){
            if (materia.getId_curso().getId().equals(curso.getId())){
                listaDeMaterias.add(materia);
            }
        }
        curso.setMaterias(listaDeMaterias);
        service.atualizar(curso.getId());
        return curso;
    }
    public void deletar(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar cursos");
        }
        CursoEntity curso = service.listarPorId(id);
        for (MateriaEntity materia : materiaService.listarTodos()){
            if (curso.equals(materia.getId_curso())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO, não foi possivel deletar pois existem materias relacionadas com o curso");
            }
        }
        for (TurmaEntity turma : turmaService.listarTodos()){
            if (curso.equals(turma.getId_curso())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO, não foi possivel deletar pois existem turmas relacionadas com o curso");
            }
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<CursoEntity> listarTodos(JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou  Docentes podem listar cursos");
        }
        List<CursoEntity> listaCursos = service.listarTodos();
        if (listaCursos.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não ha cursos para listar");
        }
        return listaCursos;
    }

    public List<MateriaEntity> listarMateriasPorIdCurso(Long id){
        List<MateriaEntity> listaMaterias = materiaService.listarPorIdCurso(id);
        if (listaMaterias.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não ha materias no curso para listar");
        }
        return listaMaterias;
    }
}
