package com.valorantassistant.match.dto;

import java.time.LocalDateTime;

public record MatchHistoryItemResponse(
    Long matchId,
    String matchCode,
    String mapName,
    String modeCode,
    LocalDateTime startedAt,
    Integer redScore,
    Integer blueScore,
    String agentCode,
    Boolean won,
    Integer kills,
    Integer deaths,
    Integer assists,
    Integer averageCombatScore
) {
}
