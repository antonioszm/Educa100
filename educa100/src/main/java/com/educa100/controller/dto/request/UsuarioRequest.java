package com.educa100.controller.dto.request;

import com.educa100.datasource.entity.PapelEntity;

public record UsuarioRequest(String login, String senha, Long id_papel) {
}
