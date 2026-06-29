package com.valorantassistant.user.dto;

public record UserAvatarUploadResponse(
    Long userId,
    String avatarUrl,
    String objectKey,
    String originalFilename,
    long size
) {
}
