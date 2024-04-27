package com.educa100.controller.dto.response;

import java.util.List;

public record TurmaResponse(Long id, String nome, List<Long> alunos, Long professor, Long curso) {
}
