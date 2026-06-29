package com.valorantassistant.media.dto;

public record ImageUploadResponse(
    String objectKey,
    String url,
    String originalFilename,
    String contentType,
    long size
) {
}
