package com.valorantassistant.match.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.valorantassistant.match.dto.MatchDetailResponse;
import com.valorantassistant.match.dto.MatchHistoryItemResponse;
import com.valorantassistant.match.dto.MatchHistoryResponse;
import com.valorantassistant.match.mapper.PlayerMatchStatsMapper;
import com.valorantassistant.match.query.MatchDetailRow;
import com.valorantassistant.match.query.MatchHistoryRow;
import com.valorantassistant.player.domain.Player;
import com.valorantassistant.player.mapper.PlayerMapper;
import com.valorantassistant.user.domain.User;
import com.valorantassistant.user.mapper.UserMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatchQueryService {

    private final UserMapper userMapper;
    private final PlayerMapper playerMapper;
    private final PlayerMatchStatsMapper playerMatchStatsMapper;

    public MatchQueryService(
        UserMapper userMapper,
        PlayerMapper playerMapper,
        PlayerMatchStatsMapper playerMatchStatsMapper
    ) {
        this.userMapper = userMapper;
        this.playerMapper = playerMapper;
        this.playerMatchStatsMapper = playerMatchStatsMapper;
    }

    public MatchHistoryResponse getMatchHistory(String username, Long playerId, int page, int size) {
        Player player = getAuthorizedPlayer(username, playerId);
        Page<MatchHistoryRow> historyPage = Page.of(page + 1L, size);
        List<MatchHistoryRow> rows = playerMatchStatsMapper.selectHistoryByPlayerId(historyPage, player.getId());

        List<MatchHistoryItemResponse> items = rows.stream()
            .map(this::toHistoryItem)
            .toList();

        return new MatchHistoryResponse(player.getId(), historyPage.getTotal(), page, size, items);
    }

    public MatchDetailResponse getMatchDetail(String username, Long playerId, Long matchId) {
        getAuthorizedPlayer(username, playerId);
        MatchDetailRow row = playerMatchStatsMapper.selectMatchDetail(playerId, matchId);
        if (row == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match record not found");
        }

        return new MatchDetailResponse(
            playerId,
            row.getMatchId(),
            row.getMatchCode(),
            row.getMapName(),
            row.getModeCode(),
            row.getStartedAt(),
            row.getEndedAt(),
            row.getDurationSeconds(),
            row.getRedScore(),
            row.getBlueScore(),
            row.getWinningTeam(),
            row.getTeamCode(),
            row.getAgentCode(),
            row.getWon(),
            row.getKills(),
            row.getDeaths(),
            row.getAssists(),
            row.getAverageCombatScore(),
            row.getDamageDealt(),
            row.getDamageReceived(),
            row.getHeadshotRate(),
            row.getKastRate(),
            row.getFirstKills(),
            row.getFirstDeaths(),
            row.getPlants(),
            row.getDefuses(),
            row.getRankTier()
        );
    }

    private Player getAuthorizedPlayer(String username, Long playerId) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User does not exist");
        }

        Player player = playerMapper.selectByIdAndUserId(playerId, user.getId());
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found or access denied");
        }
        return player;
    }

    private MatchHistoryItemResponse toHistoryItem(MatchHistoryRow row) {
        return new MatchHistoryItemResponse(
            row.getMatchId(),
            row.getMatchCode(),
            row.getMapName(),
            row.getModeCode(),
            row.getStartedAt(),
            row.getRedScore(),
            row.getBlueScore(),
            row.getAgentCode(),
            row.getWon(),
            row.getKills(),
            row.getDeaths(),
            row.getAssists(),
            row.getAverageCombatScore()
        );
    }
}
