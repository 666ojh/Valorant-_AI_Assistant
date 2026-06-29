package com.valorantassistant.media.controller;

import com.valorantassistant.common.api.ApiResponse;
import com.valorantassistant.media.dto.ImageUploadResponse;
import com.valorantassistant.media.service.ImageStorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final ImageStorageService imageStorageService;

    public MediaController(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    @PostMapping("/images")
    public ApiResponse<ImageUploadResponse> uploadImage(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "folder", required = false) String folder
    ) {
        return ApiResponse.success(imageStorageService.uploadImage(file, folder));
    }
}
