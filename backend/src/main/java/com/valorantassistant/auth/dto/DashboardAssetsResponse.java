package com.valorantassistant.auth.dto;

import java.util.Map;

public record DashboardAssetsResponse(
    boolean enabled,
    String ossBaseUrl,
    String logoUrl,
    String defaultAvatarUrl,
    String sideAgentUrl,
    Map<String, String> mapImages,
    Map<String, String> agentImages
) {
}
