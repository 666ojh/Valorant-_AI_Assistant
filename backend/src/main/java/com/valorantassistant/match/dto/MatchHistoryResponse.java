package com.valorantassistant.match.dto;

import java.util.List;

public record MatchHistoryResponse(
    Long playerId,
    long total,
    int page,
    int size,
    List<MatchHistoryItemResponse> records
) {
}
