package com.valorantassistant.common.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.assets.dashboard")
public class DashboardAssetsProperties {

    private String ossBaseUrl;
    private String logoPath;
    private String defaultAvatarPath;
    private String sideAgentPath;
    private Map<String, String> mapImages = new LinkedHashMap<>();
    private Map<String, String> agentImages = new LinkedHashMap<>();

    public String getOssBaseUrl() {
        return ossBaseUrl;
    }

    public void setOssBaseUrl(String ossBaseUrl) {
        this.ossBaseUrl = ossBaseUrl;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDefaultAvatarPath() {
        return defaultAvatarPath;
    }

    public void setDefaultAvatarPath(String defaultAvatarPath) {
        this.defaultAvatarPath = defaultAvatarPath;
    }

    public String getSideAgentPath() {
        return sideAgentPath;
    }

    public void setSideAgentPath(String sideAgentPath) {
        this.sideAgentPath = sideAgentPath;
    }

    public Map<String, String> getMapImages() {
        return mapImages;
    }

    public void setMapImages(Map<String, String> mapImages) {
        this.mapImages = mapImages;
    }

    public Map<String, String> getAgentImages() {
        return agentImages;
    }

    public void setAgentImages(Map<String, String> agentImages) {
        this.agentImages = agentImages;
    }
}
