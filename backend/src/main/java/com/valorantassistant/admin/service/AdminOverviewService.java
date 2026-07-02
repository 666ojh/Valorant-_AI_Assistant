package com.valorantassistant.admin.service;

import com.valorantassistant.admin.dto.AdminPlayerOverviewResponse;
import com.valorantassistant.admin.dto.AdminUserOverviewItemResponse;
import com.valorantassistant.admin.dto.AdminUserOverviewResponse;
import com.valorantassistant.admin.mapper.AdminOverviewMapper;
import com.valorantassistant.admin.query.AdminUserPlayerOverviewRow;
import com.valorantassistant.common.config.DashboardAssetService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AdminOverviewService {

    private final AdminOverviewMapper adminOverviewMapper;
    private final DashboardAssetService dashboardAssetService;

    public AdminOverviewService(AdminOverviewMapper adminOverviewMapper, DashboardAssetService dashboardAssetService) {
        this.adminOverviewMapper = adminOverviewMapper;
        this.dashboardAssetService = dashboardAssetService;
    }

    public AdminUserOverviewResponse getUserOverview() {
        List<AdminUserPlayerOverviewRow> rows = adminOverviewMapper.selectUserOverview();
        Map<Long, AdminUserOverviewAccumulator> users = new LinkedHashMap<>();
        String defaultAvatarUrl = dashboardAssetService.getDashboardAssets().defaultAvatarUrl();

        for (AdminUserPlayerOverviewRow row : rows) {
            AdminUserOverviewAccumulator user = users.computeIfAbsent(row.getUserId(), ignored -> new AdminUserOverviewAccumulator(
                row.getUserId(),
                row.getUsername(),
                row.getEmail(),
                row.getRoleCode(),
                row.getUserStatus(),
                row.getDisplayName(),
                dashboardAssetService.resolveAssetOrDefault(row.getUserAvatarUrl(), defaultAvatarUrl),
                row.getUserCreatedAt(),
                row.getLastLoginAt()
            ));

            if (row.getPlayerId() == null) {
                continue;
            }

            user.players().add(new AdminPlayerOverviewResponse(
                row.getPlayerId(),
                row.getGameName(),
                row.getTagLine(),
                buildPlayerFullName(row.getGameName(), row.getTagLine()),
                row.getPlatform(),
                row.getRegionCode(),
                row.getAccountLevel(),
                row.getRankTier(),
                dashboardAssetService.resolveAssetOrDefault(row.getPlayerAvatarUrl(), defaultAvatarUrl),
                row.getPrimaryFlag(),
                row.getPlayerStatus(),
                row.getMatchCount() == null ? 0L : row.getMatchCount(),
                row.getWinRate(),
                row.getAverageAcs(),
                row.getKdRatio(),
                row.getAverageKills(),
                row.getAverageDeaths(),
                row.getAverageAssists(),
                row.getHeadshotRate(),
                row.getKastRate(),
                row.getLastMatchAt()
            ));
        }

        List<AdminUserOverviewItemResponse> items = users.values().stream()
            .map(item -> new AdminUserOverviewItemResponse(
                item.userId(),
                item.username(),
                item.email(),
                item.roleCode(),
                item.status(),
                item.displayName(),
                item.avatarUrl(),
                item.createdAt(),
                item.lastLoginAt(),
                List.copyOf(item.players())
            ))
            .toList();

        long totalPlayers = items.stream().mapToLong(item -> item.players().size()).sum();
        long totalTrackedMatches = items.stream()
            .flatMap(item -> item.players().stream())
            .mapToLong(player -> player.matchCount() == null ? 0L : player.matchCount())
            .sum();

        return new AdminUserOverviewResponse(items.size(), totalPlayers, totalTrackedMatches, items);
    }

    private String buildPlayerFullName(String gameName, String tagLine) {
        if (gameName == null || gameName.isBlank()) {
            return null;
        }
        if (tagLine == null || tagLine.isBlank()) {
            return gameName;
        }
        return gameName + "#" + tagLine;
    }

    private record AdminUserOverviewAccumulator(
        Long userId,
        String username,
        String email,
        String roleCode,
        String status,
        String displayName,
        String avatarUrl,
        java.time.LocalDateTime createdAt,
        java.time.LocalDateTime lastLoginAt,
        List<AdminPlayerOverviewResponse> players
    ) {
        private AdminUserOverviewAccumulator(
            Long userId,
            String username,
            String email,
            String roleCode,
            String status,
            String displayName,
            String avatarUrl,
            java.time.LocalDateTime createdAt,
            java.time.LocalDateTime lastLoginAt
        ) {
            this(userId, username, email, roleCode, status, displayName, avatarUrl, createdAt, lastLoginAt, new ArrayList<>());
        }
    }
}
