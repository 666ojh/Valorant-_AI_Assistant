package com.valorantassistant.player.dto;

public record PlayerAvatarUploadResponse(
    Long playerId,
    String avatarUrl,
    String objectKey,
    String originalFilename,
    long size
) {
}
