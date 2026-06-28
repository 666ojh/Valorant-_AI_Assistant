package com.valorantassistant.auth.service;

import com.valorantassistant.auth.dto.CurrentUserResponse;
import com.valorantassistant.auth.dto.LoginRequest;
import com.valorantassistant.auth.dto.LoginResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.valorantassistant.common.config.JwtTokenProvider;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(
        UserMapper userMapper,
        UserProfileMapper userProfileMapper,
        PasswordEncoder passwordEncoder,
        JwtTokenProvider jwtTokenProvider
    ) {
        this.userMapper = userMapper;
        this.userProfileMapper = userProfileMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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

        UserProfile profile = userProfileMapper.selectOne(new LambdaQueryWrapper<UserProfile>()
            .eq(UserProfile::getUserId, user.getId())
            .last("limit 1"));
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
}
