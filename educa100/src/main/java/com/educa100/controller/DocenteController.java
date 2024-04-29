package com.educa100.controller;

import com.educa100.controller.dto.request.DocenteRequest;
import com.educa100.controller.dto.response.DocenteResponse;
import com.educa100.controller.dto.response.creation.DocenteCreationResponse;
import com.educa100.datasource.entity.DocenteEntity;
import com.educa100.facade.DocenteFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/docentes")
@Slf4j
public class DocenteController {

    private final DocenteFacade facade;

    public DocenteController(DocenteFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<DocenteCreationResponse> criarDocente(@RequestBody DocenteRequest request, JwtAuthenticationToken jwt){
        DocenteEntity docente = facade.criarDocente(request, jwt);
        log.info("Docente cirado com sucesso!");
        return ResponseEntity.created(null).body(new DocenteCreationResponse(docente.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteResponse> listarPorId(@PathVariable Long id, JwtAuthenticationToken jwt){
        log.info("Docente com {id} foi listado" + id);
        DocenteEntity docente = facade.listarPorId(id, jwt);
        return ResponseEntity.ok(new DocenteResponse(docente.getId(),docente.getNome(),docente.getData_entrada(),docente.getId_usuario().getId(),docente.getId_usuario().getId_papel().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteResponse> atualizar(@PathVariable Long id,@RequestBody DocenteRequest request,JwtAuthenticationToken jwt){
        DocenteEntity docente = facade.atualizar(id, request, jwt);
        log.info("Docente atualizado com sucesso!");
        return ResponseEntity.ok(new DocenteResponse(docente.getId(),docente.getNome(),docente.getData_entrada(),docente.getId_usuario().getId(),docente.getId_usuario().getId_papel().getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id, JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Docente deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<List<DocenteResponse>> listarTodos(JwtAuthenticationToken jwt){
        List<DocenteEntity> listaDeDocentes = facade.listarTodos(jwt);
        List<DocenteResponse> listaDeDocentesDto = new ArrayList<>();
        for (DocenteEntity docente : listaDeDocentes){
            DocenteResponse dto = new DocenteResponse(docente.getId(),docente.getNome(),docente.getData_entrada(),docente.getId_usuario().getId(),docente.getId_usuario().getId_papel().getId());
            listaDeDocentesDto.add(dto);
        }
        log.info("Todos os docentes listados com sucesso!");
        return ResponseEntity.ok(listaDeDocentesDto);
    }
}
