package com.educa100.controller;

import com.educa100.controller.dto.request.MateriaRequest;
import com.educa100.controller.dto.response.MateriaResponse;
import com.educa100.controller.dto.response.creation.MateriaCreationResponse;
import com.educa100.datasource.entity.*;
import com.educa100.facade.MateriaFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/materias")
@Slf4j
public class MateriaController {

    private final MateriaFacade facade;

    public MateriaController(MateriaFacade facade) {
        this.facade = facade;
    }


    @PostMapping
    public ResponseEntity<MateriaCreationResponse> criarMateria(@RequestBody MateriaRequest request, JwtAuthenticationToken jwt){
        MateriaEntity materia = facade.criarMateria(request, jwt);
        log.info("Materia cirado com sucesso!");
        return ResponseEntity.created(null).body(new MateriaCreationResponse(materia.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponse> listarPorId(@PathVariable Long id,JwtAuthenticationToken jwt){
        log.info("Materia com {id} foi listado" + id);
        MateriaEntity materia = facade.listarPorId(id, jwt);

        return ResponseEntity.ok(new MateriaResponse(materia.getId(), materia.getNome(), materia.getId_curso().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponse> atualizar(@PathVariable Long id, @RequestBody MateriaRequest request,JwtAuthenticationToken jwt){
        MateriaEntity materia = facade.atualizar(id, request, jwt);
        log.info("Materia atualizado com sucesso!");
        return ResponseEntity.ok(new MateriaResponse(materia.getId(), materia.getNome(), materia.getId_curso().getId()));
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id,JwtAuthenticationToken jwt){
        facade.deletar(id, jwt);
        log.info("Materia deletada com sucesso!");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<MateriaResponse>> listarTodos(JwtAuthenticationToken jwt){
        List<MateriaEntity> listaDeMaterias = facade.listarTodos(jwt);
        log.info("Todas as Materias listadas com sucesso!");
        List<MateriaResponse> listaMateriasDto = new ArrayList<>();
        for (MateriaEntity materia : listaDeMaterias){
            MateriaResponse dto = new MateriaResponse(materia.getId(), materia.getNome(), materia.getId_curso().getId());
            listaMateriasDto.add(dto);
        }
        return ResponseEntity.ok(listaMateriasDto);
    }
}
