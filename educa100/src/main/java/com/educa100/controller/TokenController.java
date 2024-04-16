package com.educa100.controller;

import com.educa100.controller.dto.request.UsuarioRequest;
import com.educa100.controller.dto.response.LoginResponse;
import com.educa100.datasource.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<LoginResponse> login(@RequestBody UsuarioRequest usuarioRequest){

        var usuario = usuarioRepository.findByLogin(usuarioRequest.login());

        if (usuario.isEmpty()|| !usuario.get().isLoginCorreto(usuarioRequest, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Login ou Senha incorretos");
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
