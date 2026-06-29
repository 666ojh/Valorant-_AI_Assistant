package com.valorantassistant.media.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.valorantassistant.common.config.OssStorageProperties;
import com.valorantassistant.media.dto.ImageUploadResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ImageStorageService {

    private final OssStorageProperties properties;

    public ImageStorageService(OssStorageProperties properties) {
        this.properties = properties;
    }

    public ImageUploadResponse uploadImage(MultipartFile file, String folder) {
        ensureConfigured();
        validateFile(file);

        String objectKey = buildObjectKey(folder, file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setCacheControl("public, max-age=31536000");

        OSS client = new OSSClientBuilder().build(
            properties.getEndpoint(),
            properties.getAccessKeyId(),
            properties.getAccessKeySecret()
        );
        try (InputStream inputStream = file.getInputStream()) {
            client.putObject(new PutObjectRequest(properties.getBucket(), objectKey, inputStream, metadata));
        } catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read upload content", exception);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to upload image to OSS", exception);
        } finally {
            client.shutdown();
        }

        return new ImageUploadResponse(
            objectKey,
            buildPublicUrl(objectKey),
            file.getOriginalFilename(),
            file.getContentType(),
            file.getSize()
        );
    }

    private void ensureConfigured() {
        if (!StringUtils.hasText(properties.getEndpoint())
            || !StringUtils.hasText(properties.getBucket())
            || !StringUtils.hasText(properties.getAccessKeyId())
            || !StringUtils.hasText(properties.getAccessKeySecret())) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "OSS is not configured");
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is required");
        }
        String contentType = file.getContentType();
        if (!StringUtils.hasText(contentType) || !properties.getAllowedContentTypes().contains(contentType.toLowerCase(Locale.ROOT))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only JPEG, PNG, WebP, GIF, or AVIF images are supported");
        }
        if (file.getSize() > properties.getMaxFileSize().toBytes()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Image size exceeds limit of " + properties.getMaxFileSize().toMegabytes() + "MB"
            );
        }
    }

    private String buildObjectKey(String folder, String originalFilename) {
        LocalDate today = LocalDate.now();
        String extension = resolveExtension(originalFilename);
        String rootPath = normalizePath(properties.getRootPath());
        String normalizedFolder = normalizePath(folder);
        String datePath = today.getYear() + "/" + pad(today.getMonthValue()) + "/" + pad(today.getDayOfMonth());
        String randomName = UUID.randomUUID().toString().replace("-", "");

        StringBuilder objectKey = new StringBuilder();
        appendSegment(objectKey, rootPath);
        appendSegment(objectKey, normalizedFolder);
        appendSegment(objectKey, datePath);
        if (objectKey.length() > 0) {
            objectKey.append('/');
        }
        objectKey.append(randomName).append(extension);
        return objectKey.toString();
    }

    private String buildPublicUrl(String objectKey) {
        String baseUrl = normalizeBaseUrl(properties.getPublicBaseUrl());
        if (StringUtils.hasText(baseUrl)) {
            return baseUrl + "/" + objectKey;
        }
        String endpoint = properties.getEndpoint().trim();
        if (!(endpoint.startsWith("http://") || endpoint.startsWith("https://"))) {
            endpoint = "https://" + endpoint;
        }
        URI uri = URI.create(endpoint);
        String authority = uri.getAuthority();
        if (!StringUtils.hasText(authority)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OSS endpoint is invalid");
        }
        return uri.getScheme() + "://" + properties.getBucket().trim() + "." + authority + "/" + objectKey;
    }

    private String resolveExtension(String originalFilename) {
        if (!StringUtils.hasText(originalFilename)) {
            return ".jpg";
        }
        String filename = originalFilename.trim();
        int index = filename.lastIndexOf('.');
        if (index < 0 || index == filename.length() - 1) {
            return ".jpg";
        }
        String extension = filename.substring(index).toLowerCase(Locale.ROOT);
        return extension.matches("\\.[a-z0-9]{1,10}") ? extension : ".jpg";
    }

    private String normalizePath(String path) {
        if (!StringUtils.hasText(path)) {
            return null;
        }
        String value = path.trim().replace('\\', '/');
        while (value.startsWith("/")) {
            value = value.substring(1);
        }
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        if (!StringUtils.hasText(value)) {
            return null;
        }
        if (value.contains("..")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folder path is invalid");
        }
        if (!value.matches("[A-Za-z0-9/_-]+")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folder path contains unsupported characters");
        }
        return value;
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (!StringUtils.hasText(baseUrl)) {
            return null;
        }
        String value = baseUrl.trim();
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private void appendSegment(StringBuilder builder, String segment) {
        if (!StringUtils.hasText(segment)) {
            return;
        }
        if (builder.length() > 0) {
            builder.append('/');
        }
        builder.append(segment);
    }

    private String pad(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }
}
