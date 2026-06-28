package com.valorantassistant.match.query;

import java.time.LocalDateTime;

public class MatchHistoryRow {

    private Long matchId;
    private String matchCode;
    private String mapName;
    private String modeCode;
    private LocalDateTime startedAt;
    private Integer redScore;
    private Integer blueScore;
    private String agentCode;
    private Boolean won;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer averageCombatScore;

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
}
