package com.valorantassistant.admin.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdminUserPlayerOverviewRow {

    private Long userId;
    private String username;
    private String email;
    private String roleCode;
    private String userStatus;
    private String displayName;
    private String userAvatarUrl;
    private LocalDateTime userCreatedAt;
    private LocalDateTime lastLoginAt;
    private Long playerId;
    private String platform;
    private String regionCode;
    private String gameName;
    private String tagLine;
    private Integer accountLevel;
    private String rankTier;
    private String playerAvatarUrl;
    private Boolean primaryFlag;
    private String playerStatus;
    private Long matchCount;
    private BigDecimal winRate;
    private BigDecimal averageAcs;
    private BigDecimal kdRatio;
    private BigDecimal averageKills;
    private BigDecimal averageDeaths;
    private BigDecimal averageAssists;
    private BigDecimal headshotRate;
    private BigDecimal kastRate;
    private LocalDateTime lastMatchAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public LocalDateTime getUserCreatedAt() {
        return userCreatedAt;
    }

    public void setUserCreatedAt(LocalDateTime userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public Integer getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Integer accountLevel) {
        this.accountLevel = accountLevel;
    }

    public String getRankTier() {
        return rankTier;
    }

    public void setRankTier(String rankTier) {
        this.rankTier = rankTier;
    }

    public String getPlayerAvatarUrl() {
        return playerAvatarUrl;
    }

    public void setPlayerAvatarUrl(String playerAvatarUrl) {
        this.playerAvatarUrl = playerAvatarUrl;
    }

    public Boolean getPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(Boolean primaryFlag) {
        this.primaryFlag = primaryFlag;
    }

    public String getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }

    public Long getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Long matchCount) {
        this.matchCount = matchCount;
    }

    public BigDecimal getWinRate() {
        return winRate;
    }

    public void setWinRate(BigDecimal winRate) {
        this.winRate = winRate;
    }

    public BigDecimal getAverageAcs() {
        return averageAcs;
    }

    public void setAverageAcs(BigDecimal averageAcs) {
        this.averageAcs = averageAcs;
    }

    public BigDecimal getKdRatio() {
        return kdRatio;
    }

    public void setKdRatio(BigDecimal kdRatio) {
        this.kdRatio = kdRatio;
    }

    public BigDecimal getAverageKills() {
        return averageKills;
    }

    public void setAverageKills(BigDecimal averageKills) {
        this.averageKills = averageKills;
    }

    public BigDecimal getAverageDeaths() {
        return averageDeaths;
    }

    public void setAverageDeaths(BigDecimal averageDeaths) {
        this.averageDeaths = averageDeaths;
    }

    public BigDecimal getAverageAssists() {
        return averageAssists;
    }

    public void setAverageAssists(BigDecimal averageAssists) {
        this.averageAssists = averageAssists;
    }

    public BigDecimal getHeadshotRate() {
        return headshotRate;
    }

    public void setHeadshotRate(BigDecimal headshotRate) {
        this.headshotRate = headshotRate;
    }

    public BigDecimal getKastRate() {
        return kastRate;
    }

    public void setKastRate(BigDecimal kastRate) {
        this.kastRate = kastRate;
    }

    public LocalDateTime getLastMatchAt() {
        return lastMatchAt;
    }

    public void setLastMatchAt(LocalDateTime lastMatchAt) {
        this.lastMatchAt = lastMatchAt;
    }
}
