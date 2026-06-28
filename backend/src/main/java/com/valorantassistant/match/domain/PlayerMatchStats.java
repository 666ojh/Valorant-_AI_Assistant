package com.valorantassistant.match.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.valorantassistant.common.domain.BaseEntity;
import com.valorantassistant.player.domain.Player;
import java.math.BigDecimal;

@TableName("player_match_stats")
public class PlayerMatchStats extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("player_id")
    private Long playerId;

    @TableField("match_record_id")
    private Long matchRecordId;

    @TableField("team_code")
    private String teamCode;

    @TableField("agent_code")
    private String agentCode;

    @TableField("party_id")
    private String partyId;

    @TableField("won")
    private Boolean won;

    @TableField("kills")
    private Integer kills;

    @TableField("deaths")
    private Integer deaths;

    @TableField("assists")
    private Integer assists;

    @TableField("average_combat_score")
    private Integer averageCombatScore;

    @TableField("damage_dealt")
    private Integer damageDealt;

    @TableField("damage_received")
    private Integer damageReceived;

    @TableField("headshot_rate")
    private BigDecimal headshotRate;

    @TableField("first_kills")
    private Integer firstKills;

    @TableField("first_deaths")
    private Integer firstDeaths;

    @TableField("plants")
    private Integer plants;

    @TableField("defuses")
    private Integer defuses;

    @TableField("econ_rating")
    private Integer econRating;

    @TableField("kast_rate")
    private BigDecimal kastRate;

    @TableField("rank_tier")
    private String rankTier;

    @TableField(exist = false)
    private Player player;

    @TableField(exist = false)
    private MatchRecord matchRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getMatchRecordId() {
        return matchRecordId;
    }

    public void setMatchRecordId(Long matchRecordId) {
        this.matchRecordId = matchRecordId;
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

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
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

    public Integer getEconRating() {
        return econRating;
    }

    public void setEconRating(Integer econRating) {
        this.econRating = econRating;
    }

    public BigDecimal getKastRate() {
        return kastRate;
    }

    public void setKastRate(BigDecimal kastRate) {
        this.kastRate = kastRate;
    }

    public String getRankTier() {
        return rankTier;
    }

    public void setRankTier(String rankTier) {
        this.rankTier = rankTier;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MatchRecord getMatchRecord() {
        return matchRecord;
    }

    public void setMatchRecord(MatchRecord matchRecord) {
        this.matchRecord = matchRecord;
    }
}
