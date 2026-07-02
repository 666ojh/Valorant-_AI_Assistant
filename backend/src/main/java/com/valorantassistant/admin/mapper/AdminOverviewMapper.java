package com.valorantassistant.admin.mapper;

import com.valorantassistant.admin.query.AdminUserPlayerOverviewRow;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminOverviewMapper {

    @Select("""
        select
            u.id as user_id,
            u.username,
            u.email,
            u.role_code,
            u.status as user_status,
            up.display_name,
            up.avatar_url as user_avatar_url,
            u.created_at as user_created_at,
            u.last_login_at,
            p.id as player_id,
            p.platform,
            p.region_code,
            p.game_name,
            p.tag_line,
            p.account_level,
            p.rank_tier,
            p.avatar_url as player_avatar_url,
            p.is_primary as primary_flag,
            p.status as player_status,
            stats.match_count,
            stats.win_rate,
            stats.average_acs,
            stats.kd_ratio,
            stats.average_kills,
            stats.average_deaths,
            stats.average_assists,
            stats.headshot_rate,
            stats.kast_rate,
            stats.last_match_at
        from `user` u
        left join user_profile up on up.user_id = u.id
        left join player p on p.user_id = u.id
        left join (
            select
                pms.player_id,
                count(*) as match_count,
                round(avg(case when pms.won then 100 else 0 end), 1) as win_rate,
                round(avg(pms.average_combat_score), 1) as average_acs,
                round(ifnull(sum(pms.kills) / nullif(sum(pms.deaths), 0), sum(pms.kills)), 2) as kd_ratio,
                round(avg(pms.kills), 1) as average_kills,
                round(avg(pms.deaths), 1) as average_deaths,
                round(avg(pms.assists), 1) as average_assists,
                round(avg(pms.headshot_rate), 1) as headshot_rate,
                round(avg(pms.kast_rate), 1) as kast_rate,
                max(mr.started_at) as last_match_at
            from player_match_stats pms
            inner join match_record mr on mr.id = pms.match_record_id
            group by pms.player_id
        ) stats on stats.player_id = p.id
        where u.deleted_at is null
        order by u.created_at desc, p.is_primary desc, p.id asc
        """)
    List<AdminUserPlayerOverviewRow> selectUserOverview();
}
