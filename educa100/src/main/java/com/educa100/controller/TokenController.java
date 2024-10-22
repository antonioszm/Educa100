package com.educa100.controller;

import com.educa100.controller.dto.request.LoginResquest;
import com.educa100.controller.dto.response.LoginResponse;
import com.educa100.datasource.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;

    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginResquest loginRequest){

        var usuario = usuarioRepository.findByLogin(loginRequest.login());

        if (usuario.isEmpty()|| !usuario.get().isLoginCorreto(loginRequest, bCryptPasswordEncoder)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Login ou Senha incorretos");
        }
        var agora = Instant.now();
        var expiraEm = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("Backend")
                .subject(String.valueOf(usuario.get().getId()))
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expiraEm))
                .build();

        var jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwt, expiraEm));
    }
}
