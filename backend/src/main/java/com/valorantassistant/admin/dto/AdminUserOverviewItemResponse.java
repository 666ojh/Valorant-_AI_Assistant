package com.valorantassistant.admin.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AdminUserOverviewItemResponse(
    Long userId,
    String username,
    String email,
    String roleCode,
    String status,
    String displayName,
    String avatarUrl,
    LocalDateTime createdAt,
    LocalDateTime lastLoginAt,
    List<AdminPlayerOverviewResponse> players
) {
}
