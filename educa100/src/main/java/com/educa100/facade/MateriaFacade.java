package com.educa100.facade;

import com.educa100.controller.dto.request.MateriaRequest;
import com.educa100.datasource.entity.*;
import com.educa100.service.CursoServiceImpl;
import com.educa100.service.MateriaServiceImpl;
import com.educa100.service.NotaServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MateriaFacade {

    private final MateriaServiceImpl service;

    private final CursoServiceImpl cursoService;
    private final UsuarioServiceImpl usuarioService;
    private final NotaServiceImpl notaService;


    public MateriaFacade(MateriaServiceImpl service, CursoServiceImpl cursoService, UsuarioServiceImpl usuarioService, NotaServiceImpl notaService) {
        this.service = service;
        this.cursoService = cursoService;
        this.usuarioService = usuarioService;
        this.notaService = notaService;
    }


    public MateriaEntity criarMateria(MateriaRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores criar Materias");
        }
        MateriaEntity materia = new MateriaEntity();
        boolean nomeEmUso = false;
        List<MateriaEntity> materias = service.listarTodos();
        for (MateriaEntity m : materias){
            if (m.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            materia.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é invalido");
        }
        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }else{
            log.error("Curso é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso é nullo");
        }
        materia.setId_curso(cursoValido);
        service.salvar(materia);

        return materia;
    }

    public MateriaEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        MateriaEntity materia = service.listarPorId(id);
        return materia;
    }

    public MateriaEntity atualizar(Long id, MateriaRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores atualizar Materias");
        }

        MateriaEntity materia = service.listarPorId(id);
        boolean nomeEmUso = false;
        List<MateriaEntity> materias = service.listarTodos();
        for (MateriaEntity m : materias){
            if (m.getNome().equals(request.nome())){
                nomeEmUso = true;
            }
        }
        if (request.nome().equals(materia.getNome())){
            nomeEmUso = false;
        }
        if (!request.nome().isBlank() && !nomeEmUso){
            materia.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nome é invalido");
        }

        Optional<CursoEntity> curso = Optional.ofNullable(cursoService.listarPorId(request.id_curso()));
        CursoEntity cursoValido = null;
        if (curso.isPresent()){
            cursoValido = curso.get();
        }else{
            log.error("Curso é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Curso é nullo");
        }
        materia.setId_curso(cursoValido);
        service.atualizar(materia.getId());
        return materia;
    }

    public void deletar(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId()  != 1){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar materias");
        }
        MateriaEntity materia = service.listarPorId(id);
        for (NotaEntity nota : notaService.listarTodos()){
            if (materia.equals(nota.getId_materia())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERRO, não foi possivel deletar pois existem notas relacionadas com o materia");
            }
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<MateriaEntity> listarTodos(JwtAuthenticationToken jwt){
        List<MateriaEntity> listaMaterias = service.listarTodos();
        if (listaMaterias.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não a Materias para serem listadas");
        }
        return listaMaterias;
    }

    public  List<MateriaEntity> listaCursoId(Long id, JwtAuthenticationToken jwt){
        List<MateriaEntity> listaMaterias = service.listarPorIdCurso(id);
        if (listaMaterias.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Não a Materias em Curso com id:" + id +" para serem listadas");
        }
        return listaMaterias;
    }
}
