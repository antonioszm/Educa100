package com.educa100.infra.security;

import com.educa100.datasource.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdmUsuarioConfig implements CommandLineRunner {

    private PapelRepository papelRepository;
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void run(String... args) throws Exception {

    }
}
