package com.valorantassistant.match.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MatchDetailRow {

    private Long matchId;
    private String matchCode;
    private String mapName;
    private String modeCode;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Integer durationSeconds;
    private Integer redScore;
    private Integer blueScore;
    private String winningTeam;
    private String teamCode;
    private String agentCode;
    private Boolean won;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer averageCombatScore;
    private Integer damageDealt;
    private Integer damageReceived;
    private BigDecimal headshotRate;
    private BigDecimal kastRate;
    private Integer firstKills;
    private Integer firstDeaths;
    private Integer plants;
    private Integer defuses;
    private String rankTier;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getModeCode() {
        return modeCode;
    }

    public void setModeCode(String modeCode) {
        this.modeCode = modeCode;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Integer getRedScore() {
        return redScore;
    }

    public void setRedScore(Integer redScore) {
        this.redScore = redScore;
    }

    public Integer getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(Integer blueScore) {
        this.blueScore = blueScore;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public Boolean getWon() {
        return won;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getAverageCombatScore() {
        return averageCombatScore;
    }

    public void setAverageCombatScore(Integer averageCombatScore) {
        this.averageCombatScore = averageCombatScore;
    }

    public Integer getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(Integer damageDealt) {
        this.damageDealt = damageDealt;
    }

    public Integer getDamageReceived() {
        return damageReceived;
    }

    public void setDamageReceived(Integer damageReceived) {
        this.damageReceived = damageReceived;
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

    public Integer getFirstKills() {
        return firstKills;
    }

    public void setFirstKills(Integer firstKills) {
        this.firstKills = firstKills;
    }

    public Integer getFirstDeaths() {
        return firstDeaths;
    }

    public void setFirstDeaths(Integer firstDeaths) {
        this.firstDeaths = firstDeaths;
    }

    public Integer getPlants() {
        return plants;
    }

    public void setPlants(Integer plants) {
        this.plants = plants;
    }

    public Integer getDefuses() {
        return defuses;
    }

    public void setDefuses(Integer defuses) {
        this.defuses = defuses;
    }

    public String getRankTier() {
        return rankTier;
    }

    public void setRankTier(String rankTier) {
        this.rankTier = rankTier;
    }
}
