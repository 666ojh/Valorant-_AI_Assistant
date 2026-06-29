package com.valorantassistant.common.config;

import com.valorantassistant.auth.dto.DashboardAssetsResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DashboardAssetService {

    private final DashboardAssetsProperties properties;

    public DashboardAssetService(DashboardAssetsProperties properties) {
        this.properties = properties;
    }

    public DashboardAssetsResponse getDashboardAssets() {
        return new DashboardAssetsResponse(
            isEnabled(),
            normalizeBaseUrl(properties.getOssBaseUrl()),
            resolveAsset(properties.getLogoPath()),
            resolveAsset(properties.getDefaultAvatarPath()),
            resolveAsset(properties.getSideAgentPath()),
            resolveMap(properties.getMapImages()),
            resolveMap(properties.getAgentImages())
        );
    }

    public String resolveAsset(String pathOrUrl) {
        if (!StringUtils.hasText(pathOrUrl)) {
            return null;
        }
        String value = pathOrUrl.trim();
        if (value.startsWith("http://") || value.startsWith("https://")) {
            return value;
        }
        if (value.startsWith("/")) {
            return value;
        }
        String baseUrl = normalizeBaseUrl(properties.getOssBaseUrl());
        if (!StringUtils.hasText(baseUrl)) {
            return null;
        }
        String normalizedPath = value.startsWith("/") ? value.substring(1) : value;
        return baseUrl + "/" + normalizedPath;
    }

    public String resolveAssetOrDefault(String pathOrUrl, String defaultUrl) {
        String resolved = resolveAsset(pathOrUrl);
        return StringUtils.hasText(resolved) ? resolved : defaultUrl;
    }

    private boolean isEnabled() {
        return StringUtils.hasText(properties.getOssBaseUrl());
    }

    private Map<String, String> resolveMap(Map<String, String> source) {
        Map<String, String> target = new LinkedHashMap<>();
        if (source == null) {
            return target;
        }
        source.forEach((key, value) -> {
            if (StringUtils.hasText(key)) {
                target.put(key, resolveAsset(value));
            }
        });
        return target;
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (!StringUtils.hasText(baseUrl)) {
            return null;
        }
        String value = baseUrl.trim();
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }
}
