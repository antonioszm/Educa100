package com.educa100.facade;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.controller.dto.response.DocenteResponse;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.service.DocenteServiceImpl;
import com.educa100.service.UsuarioServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocenteFacade {

    private final DocenteServiceImpl service;
    private final UsuarioServiceImpl usuarioService;
    public DocenteFacade(DocenteServiceImpl service, UsuarioServiceImpl usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    public DocenteEntity criarDocente(DocenteRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().equals(5)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou docentes podem criar docentes");
        }
        DocenteEntity docente = new DocenteEntity();
        if (!request.nome().isBlank()){
            docente.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        docente.setData_entrada(request.dataEntrada());

        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        } else{
            log.error("Usuario é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!usuarioLogado.getId_papel().equals(1) && !usuarioValido.getId_papel().equals(usuarioLogado.getId_papel())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO,você so pode cadastrar você mesmo");
        }
        docente.setId_usuario(usuarioValido);

        service.salvar(docente);

        return docente;
    }

    public DocenteEntity listarPorId(Long id, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().equals(5)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou docentes podem listar docentes");
        }
        DocenteEntity docente = service.listarPorId(id);
        if (!usuarioLogado.getId_papel().equals(1) || docente.getId_usuario().equals(usuarioLogado.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou o próprio docente podem se listar");
        }
        return docente;
    }

    public DocenteEntity atualizar( Long id, DocenteRequest request, JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().equals(5)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou docentes podem atualizar docentes");
        }
        DocenteEntity docente = service.listarPorId(id);
        if (!usuarioLogado.getId_papel().equals(1) || docente.getId_usuario().equals(usuarioLogado.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores ou o próprio docente podem atualizar docentes");
        }
        Optional<UsuarioEntity> usuario = Optional.ofNullable(usuarioService.listarPorId(request.id_usuario()));
        UsuarioEntity usuarioValido = null;
        if (usuario.isPresent()){
            usuarioValido = usuario.get();
        }else{
            log.error("Usuario é nullo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        docente.setId_usuario(usuarioValido);
        if (!request.nome().isBlank()){
            docente.setNome(request.nome());
        } else {
            log.error("Nome Invalido");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        docente.setData_entrada(request.dataEntrada());
        service.atualizar(docente.getId());
        return docente;
    }

    public void deletar(Long id,JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (!usuarioLogado.getId_papel().equals(1)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem deletar docentes");
        }
        service.removerPorId(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public List<DocenteEntity> listarTodos(JwtAuthenticationToken jwt){
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().equals(1)) {
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