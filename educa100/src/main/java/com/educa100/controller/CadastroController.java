package com.educa100.controller;

import com.educa100.controller.dto.request.UsuarioRequest;
import com.educa100.datasource.entity.PapelEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.datasource.repository.PapelRepository;
import com.educa100.datasource.repository.UsuarioRepository;
import com.educa100.infra.exception.PapelNotFoundException;
import com.educa100.service.UsuarioServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class CadastroController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioServiceImpl usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final PapelRepository papelRepository;

    public CadastroController(UsuarioRepository usuarioRepository, UsuarioServiceImpl usuarioService, BCryptPasswordEncoder passwordEncoder, PapelRepository papelRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.papelRepository = papelRepository;
    }

    @Transactional
    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastro(@RequestBody UsuarioRequest usuarioRequest, JwtAuthenticationToken jwt) throws Exception {
        UsuarioEntity usuarioLogado = usuarioService.listarPorId(Long.valueOf(jwt.getName()));
        if (usuarioLogado.getId_papel().getId() != 1 ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "ACESSO NEGADO, só adiministradores podem criar Usuarios");
        }
        Optional<UsuarioEntity> usuarioComNomeIgual = usuarioRepository.findByLogin(usuarioRequest.login());

        if (usuarioComNomeIgual.isPresent()){
            throw new Exception("Este nome de usuario não esta disponivel");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setLogin(usuarioRequest.login());
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.senha()));


        PapelEntity papel = papelRepository.findById(usuarioRequest.id_papel())
                .orElseThrow(() -> new PapelNotFoundException(usuarioRequest.id_papel()));
        usuario.setId_papel(papel);

        usuarioRepository.save(usuario);


        return ResponseEntity.created(null).build();
    }
}
