package com.valorantassistant.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminPlayerOverviewResponse(
    Long playerId,
    String gameName,
    String tagLine,
    String fullName,
    String platform,
    String regionCode,
    Integer accountLevel,
    String rankTier,
    String avatarUrl,
    Boolean primary,
    String status,
    Long matchCount,
    BigDecimal winRate,
    BigDecimal averageAcs,
    BigDecimal kdRatio,
    BigDecimal averageKills,
    BigDecimal averageDeaths,
    BigDecimal averageAssists,
    BigDecimal headshotRate,
    BigDecimal kastRate,
    LocalDateTime lastMatchAt
) {
}
