package com.valorantassistant.player.service;

import com.valorantassistant.media.dto.ImageUploadResponse;
import com.valorantassistant.media.service.ImageStorageService;
import com.valorantassistant.player.domain.Player;
import com.valorantassistant.player.dto.PlayerAvatarUploadResponse;
import com.valorantassistant.player.mapper.PlayerMapper;
import com.valorantassistant.user.domain.User;
import com.valorantassistant.user.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerProfileService {

    private final PlayerMapper playerMapper;
    private final UserMapper userMapper;
    private final ImageStorageService imageStorageService;

    public PlayerProfileService(
        PlayerMapper playerMapper,
        UserMapper userMapper,
        ImageStorageService imageStorageService
    ) {
        this.playerMapper = playerMapper;
        this.userMapper = userMapper;
        this.imageStorageService = imageStorageService;
    }

    public PlayerAvatarUploadResponse uploadPlayerAvatar(String username, Long playerId, MultipartFile file) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist");
        }

        Player player = playerMapper.selectByIdAndUserId(playerId, user.getId());
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player does not exist");
        }

        ImageUploadResponse uploadResponse = imageStorageService.uploadImage(file, "players/" + playerId + "/avatar");
        player.setAvatarUrl(uploadResponse.url());
        playerMapper.updateById(player);

        return new PlayerAvatarUploadResponse(
            player.getId(),
            uploadResponse.url(),
            uploadResponse.objectKey(),
            uploadResponse.originalFilename(),
            uploadResponse.size()
        );
    }
}
