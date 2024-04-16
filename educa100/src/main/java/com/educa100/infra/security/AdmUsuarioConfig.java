package com.educa100.infra.security;

import com.educa100.datasource.entity.PapelEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.datasource.repository.PapelRepository;
import com.educa100.datasource.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdmUsuarioConfig implements CommandLineRunner {

    private PapelRepository papelRepository;
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdmUsuarioConfig(PapelRepository papelRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.papelRepository = papelRepository;
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var papelAdm = papelRepository.findByNome(PapelEntity.Papel.ADM.name());

        var usuarioAdm = usuarioRepository.findByLogin("adm");
        usuarioAdm.ifPresentOrElse(
                (usuario) -> {
                    System.out.println("ADM já criado");
                },
                () -> {
                    var usuario = new UsuarioEntity();
                    usuario.setLogin("adm");
                    usuario.setSenha(bCryptPasswordEncoder.encode("1000"));
                }
        );
    }
}
