package com.valorantassistant.auth.dto;

public record CurrentUserResponse(
    Long userId,
    String username,
    String email,
    String roleCode,
    String status,
    String displayName
) {
}
