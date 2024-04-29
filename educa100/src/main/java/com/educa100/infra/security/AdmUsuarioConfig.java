package com.educa100.infra.security;

import com.educa100.datasource.entity.PapelEntity;
import com.educa100.datasource.entity.UsuarioEntity;
import com.educa100.datasource.repository.PapelRepository;
import com.educa100.datasource.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
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
        PapelEntity papelAdm = new PapelEntity();
        papelAdm.setNome(PapelEntity.Papel.ADM.name());
        papelRepository.save(papelAdm);
        System.out.println("Papel ADM criado e salvo com sucesso.");

        var usuarioAdm = usuarioRepository.findByLogin("adm");
        usuarioAdm.ifPresentOrElse(
                (usuario) -> {
                    System.out.println("ADM já criado");
                },
                () -> {
                    var usuario = new UsuarioEntity();
                    usuario.setLogin("adm");
                    usuario.setSenha(bCryptPasswordEncoder.encode("12345"));
                    usuario.setId_papel(papelAdm);
                    usuarioRepository.save(usuario);
                }
        );
        PapelEntity papelPedagogo = new PapelEntity();
        papelPedagogo.setNome(PapelEntity.Papel.PEDAGOGICO.name());
        papelRepository.save(papelPedagogo);

        PapelEntity papelRecruiter = new PapelEntity();
        papelRecruiter.setNome(PapelEntity.Papel.RECRUITER.name());
        papelRepository.save(papelRecruiter);

        PapelEntity papelProfessor = new PapelEntity();
        papelProfessor.setNome(PapelEntity.Papel.PROFESSOR.name());
        papelRepository.save(papelProfessor);

        PapelEntity papelAluno = new PapelEntity();
        papelAluno.setNome(PapelEntity.Papel.ALUNO.name());
        papelRepository.save(papelAluno);
    }
}
