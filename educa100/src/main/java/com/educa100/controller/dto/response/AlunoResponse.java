package com.educa100.controller.dto.response;

import java.time.LocalDate;

public record AlunoResponse(Long id,String nome, LocalDate dataNascimento, Long id_usuario, Long id_turma) {
}
