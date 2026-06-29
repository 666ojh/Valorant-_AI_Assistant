package com.valorantassistant.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.valorantassistant.media.dto.ImageUploadResponse;
import com.valorantassistant.media.service.ImageStorageService;
import com.valorantassistant.user.domain.User;
import com.valorantassistant.user.domain.UserProfile;
import com.valorantassistant.user.dto.UserAvatarUploadResponse;
import com.valorantassistant.user.mapper.UserMapper;
import com.valorantassistant.user.mapper.UserProfileMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserProfileService {

    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;
    private final ImageStorageService imageStorageService;

    public UserProfileService(
        UserMapper userMapper,
        UserProfileMapper userProfileMapper,
        ImageStorageService imageStorageService
    ) {
        this.userMapper = userMapper;
        this.userProfileMapper = userProfileMapper;
        this.imageStorageService = imageStorageService;
    }

    public UserAvatarUploadResponse uploadCurrentUserAvatar(String username, MultipartFile file) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist");
        }

        ImageUploadResponse uploadResponse = imageStorageService.uploadImage(file, "users/" + user.getId() + "/avatar");
        UserProfile profile = findOrCreateProfile(user.getId());
        profile.setAvatarUrl(uploadResponse.url());

        if (userProfileMapper.selectByUserId(user.getId()) == null) {
            userProfileMapper.insert(profile);
        } else {
            userProfileMapper.updateById(profile);
        }

        return new UserAvatarUploadResponse(
            user.getId(),
            uploadResponse.url(),
            uploadResponse.objectKey(),
            uploadResponse.originalFilename(),
            uploadResponse.size()
        );
    }

    private UserProfile findOrCreateProfile(Long userId) {
        UserProfile profile = userProfileMapper.selectByUserId(userId);
        if (profile != null) {
            return profile;
        }
        profile = userProfileMapper.selectOne(new LambdaQueryWrapper<UserProfile>()
            .eq(UserProfile::getUserId, userId)
            .last("limit 1"));
        if (profile != null) {
            return profile;
        }
        UserProfile created = new UserProfile();
        created.setUserId(userId);
        return created;
    }
}
