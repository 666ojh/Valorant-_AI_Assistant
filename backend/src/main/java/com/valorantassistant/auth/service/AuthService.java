package com.valorantassistant.auth.service;

import com.valorantassistant.auth.dto.CurrentUserResponse;
import com.valorantassistant.auth.dto.CurrentPlayerResponse;
import com.valorantassistant.auth.dto.DashboardAssetsResponse;
import com.valorantassistant.auth.dto.LoginRequest;
import com.valorantassistant.auth.dto.LoginResponse;
import com.valorantassistant.auth.dto.SessionContextResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.valorantassistant.common.config.DashboardAssetService;
import com.valorantassistant.common.config.JwtTokenProvider;
import com.valorantassistant.player.domain.Player;
import com.valorantassistant.player.mapper.PlayerMapper;
import com.valorantassistant.user.domain.User;
import com.valorantassistant.user.domain.UserProfile;
import com.valorantassistant.user.domain.UserStatus;
import com.valorantassistant.user.mapper.UserMapper;
import com.valorantassistant.user.mapper.UserProfileMapper;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;
    private final PlayerMapper playerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final DashboardAssetService dashboardAssetService;

    public AuthService(
        UserMapper userMapper,
        UserProfileMapper userProfileMapper,
        PlayerMapper playerMapper,
        PasswordEncoder passwordEncoder,
        JwtTokenProvider jwtTokenProvider,
        DashboardAssetService dashboardAssetService
    ) {
        this.userMapper = userMapper;
        this.userProfileMapper = userProfileMapper;
        this.playerMapper = playerMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.dashboardAssetService = dashboardAssetService;
    }

    public LoginResponse login(LoginRequest request, String clientIp) {
        User user = userMapper.selectByUsernameOrEmail(request.usernameOrEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not allowed to log in");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        user.setLastLoginAt(LocalDateTime.now());
        user.setLastLoginIp(clientIp);
        userMapper.updateById(user);

        String accessToken = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRoleCode());
        return new LoginResponse(accessToken, user.getId(), user.getUsername(), user.getRoleCode());
    }

    public CurrentUserResponse getCurrentUser(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist");
        }

        UserProfile profile = findProfile(user.getId());
        String displayName = profile == null ? null : profile.getDisplayName();
        return new CurrentUserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRoleCode(),
            user.getStatus().name(),
            displayName
        );
    }

    public SessionContextResponse getSessionContext(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist");
        }

        UserProfile profile = findProfile(user.getId());
        Player primaryPlayer = findPrimaryPlayer(user.getId());
        DashboardAssetsResponse assets = dashboardAssetService.getDashboardAssets();
        String defaultAvatarUrl = assets.defaultAvatarUrl();
        String userAvatarUrl = dashboardAssetService.resolveAssetOrDefault(profile == null ? null : profile.getAvatarUrl(), defaultAvatarUrl);

        CurrentPlayerResponse currentPlayer = primaryPlayer == null ? null : new CurrentPlayerResponse(
            primaryPlayer.getId(),
            primaryPlayer.getUserId(),
            primaryPlayer.getGameName(),
            primaryPlayer.getTagLine(),
            buildPlayerFullName(primaryPlayer),
            primaryPlayer.getPlatform(),
            primaryPlayer.getRegionCode(),
            primaryPlayer.getAccountLevel(),
            primaryPlayer.getRankTier(),
            dashboardAssetService.resolveAssetOrDefault(primaryPlayer.getAvatarUrl(), defaultAvatarUrl),
            primaryPlayer.getPrimary(),
            primaryPlayer.getStatus()
        );

        return new SessionContextResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRoleCode(),
            user.getStatus().name(),
            profile == null ? null : profile.getDisplayName(),
            userAvatarUrl,
            currentPlayer,
            assets
        );
    }

    private UserProfile findProfile(Long userId) {
        UserProfile profile = userProfileMapper.selectByUserId(userId);
        if (profile != null) {
            return profile;
        }
        return userProfileMapper.selectOne(new LambdaQueryWrapper<UserProfile>()
            .eq(UserProfile::getUserId, userId)
            .last("limit 1"));
    }

    private Player findPrimaryPlayer(Long userId) {
        Player player = playerMapper.selectPrimaryByUserId(userId);
        if (player != null) {
            return player;
        }
        return playerMapper.selectFirstByUserId(userId);
    }

    private String buildPlayerFullName(Player player) {
        if (player == null) {
            return null;
        }
        if (player.getGameName() == null) {
            return null;
        }
        return player.getTagLine() == null || player.getTagLine().isBlank()
            ? player.getGameName()
            : player.getGameName() + "#" + player.getTagLine();
    }
}
