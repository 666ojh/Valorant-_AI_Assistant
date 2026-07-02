package com.valorantassistant.player.dto;

public record PlayerSummaryResponse(
    Long playerId,
    Long userId,
    String gameName,
    String tagLine,
    String fullName,
    String platform,
    String regionCode,
    Integer accountLevel,
    String rankTier,
    String avatarUrl,
    Boolean primary,
    String status
) {
}
