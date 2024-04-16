package com.educa100.controller.dto.response;

public record LoginResponse(String tokenDeAcesso, Long expiresIn) {
}
