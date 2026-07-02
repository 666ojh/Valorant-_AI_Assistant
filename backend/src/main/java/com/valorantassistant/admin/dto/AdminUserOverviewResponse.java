package com.valorantassistant.admin.dto;

import java.util.List;

public record AdminUserOverviewResponse(
    long totalUsers,
    long totalPlayers,
    long totalTrackedMatches,
    List<AdminUserOverviewItemResponse> users
) {
}
