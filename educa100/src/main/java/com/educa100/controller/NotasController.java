package com.educa100.controller;

import com.educa100.controller.dto.request.NotaRequest;
import com.educa100.controller.dto.response.NotaResponse;
import com.educa100.controller.dto.response.creation.NotaCreationResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.NotaFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notas")
@Slf4j
public class NotasController {

    public final NotaFacade facade;

    public NotasController(NotaFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<NotaCreationResponse> criarNotas(@RequestBody NotaRequest request, JwtAuthenticationToken jwt){
        NotaEntity nota = facade.criarNotas(request, jwt);
        log.info("Nota cirado com sucesso!");
        return ResponseEntity.created(null).body(new NotaCreationResponse(nota.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaResponse> listarPorId(@PathVariable Long id,JwtAuthenticationToken jwt){
        log.info("Nota com {id} foi listado" + id);
        NotaEntity nota = facade.listarPorId(id, jwt);

        return ResponseEntity.ok(new NotaResponse(nota.getId(),nota.getId_aluno().getId(),nota.getId_professor().getId(),nota.getId_materia().getId(),nota.getValor(),nota.getData()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaResponse> atualizar(@PathVariable Long id, @RequestBody NotaRequest request,JwtAuthenticationToken jwt){
        NotaEntity nota = facade.atualizar(id, request, jwt);
        log.info("Nota atualizado com sucesso!");
        return ResponseEntity.ok(new NotaResponse(nota.getId(),nota.getId_aluno().getId(),nota.getId_professor().getId(),nota.getId_materia().getId(),nota.getValor(),nota.getData()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id, JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Nota deletado com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<NotaResponse>> listarTodos(JwtAuthenticationToken jwt){
        List<NotaEntity> listaDeNotas = facade.listarTodos(jwt);
        log.info("Todas as notas listadas com sucesso!");
        List<NotaResponse> listaNotasDto = new ArrayList<>();
        for (NotaEntity nota: listaDeNotas){
            NotaResponse dto = new NotaResponse(nota.getId(),nota.getId_aluno().getId(),nota.getId_professor().getId(),nota.getId_materia().getId(),nota.getValor(),nota.getData());
            listaNotasDto.add(dto);
        }
        return ResponseEntity.ok(listaNotasDto);
    }
}
