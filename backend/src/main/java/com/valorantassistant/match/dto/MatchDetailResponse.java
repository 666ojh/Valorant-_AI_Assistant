package com.valorantassistant.match.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MatchDetailResponse(
    Long playerId,
    Long matchId,
    String matchCode,
    String mapName,
    String modeCode,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    Integer durationSeconds,
    Integer redScore,
    Integer blueScore,
    String winningTeam,
    String teamCode,
    String agentCode,
    Boolean won,
    Integer kills,
    Integer deaths,
    Integer assists,
    Integer averageCombatScore,
    Integer damageDealt,
    Integer damageReceived,
    BigDecimal headshotRate,
    BigDecimal kastRate,
    Integer firstKills,
    Integer firstDeaths,
    Integer plants,
    Integer defuses,
    String rankTier
) {
}
