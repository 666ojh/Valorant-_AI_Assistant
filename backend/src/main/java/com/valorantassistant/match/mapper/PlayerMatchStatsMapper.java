package com.valorantassistant.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.valorantassistant.match.domain.PlayerMatchStats;
import com.valorantassistant.match.query.MatchDetailRow;
import com.valorantassistant.match.query.MatchHistoryRow;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PlayerMatchStatsMapper extends BaseMapper<PlayerMatchStats> {

    @Select("""
        select
            mr.id as match_id,
            mr.match_code,
            mr.map_name,
            mr.mode_code,
            mr.started_at,
            mr.red_score,
            mr.blue_score,
            pms.agent_code,
            pms.won,
            pms.kills,
            pms.deaths,
            pms.assists,
            pms.average_combat_score
        from player_match_stats pms
        inner join match_record mr on mr.id = pms.match_record_id
        where pms.player_id = #{playerId}
        order by mr.started_at desc
        """)
    List<MatchHistoryRow> selectHistoryByPlayerId(Page<MatchHistoryRow> page, @Param("playerId") Long playerId);

    @Select("""
        select
            mr.id as match_id,
            mr.match_code,
            mr.map_name,
            mr.mode_code,
            mr.started_at,
            mr.ended_at,
            mr.duration_seconds,
            mr.red_score,
            mr.blue_score,
            mr.winning_team,
            pms.team_code,
            pms.agent_code,
            pms.won,
            pms.kills,
            pms.deaths,
            pms.assists,
            pms.average_combat_score,
            pms.damage_dealt,
            pms.damage_received,
            pms.headshot_rate,
            pms.kast_rate,
            pms.first_kills,
            pms.first_deaths,
            pms.plants,
            pms.defuses,
            pms.rank_tier
        from player_match_stats pms
        inner join match_record mr on mr.id = pms.match_record_id
        where pms.player_id = #{playerId} and mr.id = #{matchId}
        limit 1
        """)
    MatchDetailRow selectMatchDetail(@Param("playerId") Long playerId, @Param("matchId") Long matchId);
}
