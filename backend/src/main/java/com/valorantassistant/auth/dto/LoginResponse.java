package com.valorantassistant.auth.dto;

public record LoginResponse(
    String accessToken,
    Long userId,
    String username,
    String roleCode
) {
}
