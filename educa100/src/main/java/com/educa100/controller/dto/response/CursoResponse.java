package com.educa100.controller.dto.response;

import java.util.List;

public record CursoResponse(Long id, String nome, List<Long> listaIdTurmas,List<Long> listaIdMaterias) {
}
