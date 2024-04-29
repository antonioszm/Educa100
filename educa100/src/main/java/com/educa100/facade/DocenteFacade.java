package com.educa100.facade;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.datasource.entity.*;
import com.educa100.service.DocenteServiceImpl;
import com.educa100.service.NotaServiceImpl;
import com.educa100.service.TurmaServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocenteFacade {

    private final DocenteServiceImpl service;
    private final UsuarioServiceImpl usuarioService;
    private final NotaServiceImpl notaService;
    private final TurmaServiceImpl turmaService;
    public DocenteFacade(DocenteServiceImpl service, UsuarioServiceImpl usuarioService, NotaServiceImpl notaService, TurmaServiceImpl turmaService) {
        this.service = service;
        this.usuarioService = usuarioService;
        this.notaService = notaService;
        this.turmaService = turmaService;
    }

    public DocenteEntity criarDocente(DocenteRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou docentes podem criar docentes");
        }
        DocenteEntity docente = new DocenteEntity();
        boolean nomeEmUso = false;
        List<DocenteEntity> listaDeDocentes = service.listarTodos();
        for (DocenteEntity docentes : listaDeDocentes){
            if (docentes.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            docente.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome Invalido");
        }
        if (request.dataEntrada() == null){
            docente.setData_entrada(LocalDate.now());
        } else {
            docente.setData_entrada(request.dataEntrada());
        }

        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        } else{
            log.error("Usuario é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (usuarioLogado.getId_papel().getId() != 1 && !usuarioValido.getId_papel().equals(usuarioLogado.getId_papel())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, Só adiministradores ou o proprio docente podem se criar o docente");
        }
        docente.setId_usuario(usuarioValido);

        service.salvar(docente);

        return docente;
    }

    public DocenteEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou docentes podem listar docentes");
        }
        DocenteEntity docente = service.listarPorId(id);
        if (usuarioLogado.getId_papel().getId() != 1 && !docente.getId_usuario().getId().equals(usuarioLogado.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou o próprio docente podem se listar");
        }
        return docente;
    }

    public DocenteEntity atualizar( Long id, DocenteRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 5){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou docentes podem atualizar docentes");
        }
        DocenteEntity docente = service.listarPorId(id);
        if (usuarioLogado.getId_papel().getId() != 1 && !docente.getId_usuario().getId().equals(usuarioLogado.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou o próprio docente podem atualizar docentes");
        }
        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        }
        if (usuarioLogado.getId_papel().getId() != 1  && !usuarioValido.getId_papel().equals(usuarioLogado.getId_papel())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, você so pode atualizar você mesmo");
        }
        boolean nomeEmUso = false;
        List<DocenteEntity> docentes = service.listarTodos();
        for (DocenteEntity d : docentes){
            if (d.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (request.nome().equals(docente.getNome())){
            nomeEmUso = false;
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            docente.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (request.dataEntrada() == null){
            docente.setData_entrada(docente.getData_entrada());
        } else {
            docente.setData_entrada(request.dataEntrada());
        }
        docente.setId_usuario(usuarioValido);
        service.atualizar(docente.getId());
        return docente;
    }

    public void deletar(Long id,JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar docentes");
        }
        DocenteEntity professor = service.listarPorId(id);
        for (NotaEntity nota : notaService.listarTodos()){
            if (professor.equals(nota.getId_professor())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO, não foi possivel deletar pois existem notas relacionadas com o professor");
            }
        }
        for (TurmaEntity turma : turmaService.listarTodos()){
            if (professor.equals(turma.getProfessor())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO, não foi possivel deletar pois existem turmas relacionadas com o professor");
            }
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<DocenteEntity> listarTodos(JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() == 1) {
            List<DocenteEntity> listaDocentes = service.listarTodos();
            if (listaDocentes.isEmpty()) {
                log.info("Não ha docentes para serem listados");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return listaDocentes;
        }else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"ACESSO NEGADO, so administradores podem listar todos os docentes");
        }
    }
}
