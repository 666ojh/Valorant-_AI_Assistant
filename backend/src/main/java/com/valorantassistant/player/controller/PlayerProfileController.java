package com.valorantassistant.player.controller;

import com.valorantassistant.common.api.ApiResponse;
import com.valorantassistant.player.dto.PlayerAvatarUploadResponse;
import com.valorantassistant.player.dto.PlayerSummaryResponse;
import com.valorantassistant.player.service.PlayerProfileService;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerProfileController {

    private final PlayerProfileService playerProfileService;

    public PlayerProfileController(PlayerProfileService playerProfileService) {
        this.playerProfileService = playerProfileService;
    }

    @GetMapping("/me")
    public ApiResponse<List<PlayerSummaryResponse>> currentPlayers(Authentication authentication) {
        return ApiResponse.success(playerProfileService.listCurrentPlayers(authentication.getName()));
    }

    @PostMapping("/{playerId}/avatar")
    public ApiResponse<PlayerAvatarUploadResponse> uploadAvatar(
        @PathVariable Long playerId,
        @RequestParam("file") MultipartFile file,
        Authentication authentication
    ) {
        return ApiResponse.success(playerProfileService.uploadPlayerAvatar(authentication.getName(), playerId, file));
    }

    @PostMapping("/{playerId}/activate")
    public ApiResponse<PlayerSummaryResponse> activatePlayer(@PathVariable Long playerId, Authentication authentication) {
        return ApiResponse.success(playerProfileService.activatePlayer(authentication.getName(), playerId));
    }
}
