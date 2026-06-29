package com.valorantassistant.auth.dto;

public record SessionContextResponse(
    Long userId,
    String username,
    String email,
    String roleCode,
    String status,
    String displayName,
    String avatarUrl,
    CurrentPlayerResponse primaryPlayer,
    DashboardAssetsResponse dashboardAssets
) {
}
