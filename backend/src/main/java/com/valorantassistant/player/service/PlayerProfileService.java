package com.valorantassistant.player.service;

import com.valorantassistant.common.config.DashboardAssetService;
import com.valorantassistant.media.dto.ImageUploadResponse;
import com.valorantassistant.media.service.ImageStorageService;
import com.valorantassistant.player.domain.Player;
import com.valorantassistant.player.dto.PlayerAvatarUploadResponse;
import com.valorantassistant.player.dto.PlayerSummaryResponse;
import com.valorantassistant.player.mapper.PlayerMapper;
import com.valorantassistant.user.domain.User;
import com.valorantassistant.user.mapper.UserMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerProfileService {

    private final PlayerMapper playerMapper;
    private final UserMapper userMapper;
    private final ImageStorageService imageStorageService;
    private final DashboardAssetService dashboardAssetService;

    public PlayerProfileService(
        PlayerMapper playerMapper,
        UserMapper userMapper,
        ImageStorageService imageStorageService,
        DashboardAssetService dashboardAssetService
    ) {
        this.playerMapper = playerMapper;
        this.userMapper = userMapper;
        this.imageStorageService = imageStorageService;
        this.dashboardAssetService = dashboardAssetService;
    }

    public PlayerAvatarUploadResponse uploadPlayerAvatar(String username, Long playerId, MultipartFile file) {
        User user = getRequiredUser(username);
        Player player = getAuthorizedPlayer(user.getId(), playerId);
        String defaultAvatarUrl = dashboardAssetService.getDashboardAssets().defaultAvatarUrl();

        ImageUploadResponse uploadResponse = imageStorageService.uploadImage(file, "players/" + playerId + "/avatar");
        player.setAvatarUrl(uploadResponse.url());
        playerMapper.updateById(player);

        return new PlayerAvatarUploadResponse(
            player.getId(),
            dashboardAssetService.resolveAssetOrDefault(uploadResponse.url(), defaultAvatarUrl),
            uploadResponse.objectKey(),
            uploadResponse.originalFilename(),
            uploadResponse.size()
        );
    }

    public List<PlayerSummaryResponse> listCurrentPlayers(String username) {
        User user = getRequiredUser(username);
        String defaultAvatarUrl = dashboardAssetService.getDashboardAssets().defaultAvatarUrl();

        return playerMapper.selectByUserId(user.getId()).stream()
            .map(player -> toPlayerSummary(player, defaultAvatarUrl))
            .toList();
    }

    @Transactional
    public PlayerSummaryResponse activatePlayer(String username, Long playerId) {
        User user = getRequiredUser(username);
        Player player = getAuthorizedPlayer(user.getId(), playerId);
        String defaultAvatarUrl = dashboardAssetService.getDashboardAssets().defaultAvatarUrl();

        playerMapper.clearPrimaryByUserId(user.getId());
        int updated = playerMapper.activateByIdAndUserId(playerId, user.getId());
        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player does not exist");
        }

        player.setPrimary(true);
        return toPlayerSummary(player, defaultAvatarUrl);
    }

    private User getRequiredUser(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist");
        }
        return user;
    }

    private Player getAuthorizedPlayer(Long userId, Long playerId) {
        Player player = playerMapper.selectByIdAndUserId(playerId, userId);
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player does not exist");
        }
        return player;
    }

    private PlayerSummaryResponse toPlayerSummary(Player player, String defaultAvatarUrl) {
        return new PlayerSummaryResponse(
            player.getId(),
            player.getUserId(),
            player.getGameName(),
            player.getTagLine(),
            buildPlayerFullName(player),
            player.getPlatform(),
            player.getRegionCode(),
            player.getAccountLevel(),
            player.getRankTier(),
            dashboardAssetService.resolveAssetOrDefault(player.getAvatarUrl(), defaultAvatarUrl),
            player.getPrimary(),
            player.getStatus()
        );
    }

    private String buildPlayerFullName(Player player) {
        if (player == null || player.getGameName() == null || player.getGameName().isBlank()) {
            return null;
        }
        if (player.getTagLine() == null || player.getTagLine().isBlank()) {
            return player.getGameName();
        }
        return player.getGameName() + "#" + player.getTagLine();
    }
}
