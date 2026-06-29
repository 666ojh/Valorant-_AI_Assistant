package com.valorantassistant.user.controller;

import com.valorantassistant.common.api.ApiResponse;
import com.valorantassistant.user.dto.UserAvatarUploadResponse;
import com.valorantassistant.user.service.UserProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/me/avatar")
    public ApiResponse<UserAvatarUploadResponse> uploadCurrentUserAvatar(
        @RequestParam("file") MultipartFile file,
        Authentication authentication
    ) {
        return ApiResponse.success(userProfileService.uploadCurrentUserAvatar(authentication.getName(), file));
    }
}
