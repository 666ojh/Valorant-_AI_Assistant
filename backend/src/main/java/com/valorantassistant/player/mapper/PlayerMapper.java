package com.valorantassistant.player.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.valorantassistant.player.domain.Player;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PlayerMapper extends BaseMapper<Player> {

    @Select("""
        select id, user_id, platform, region_code, game_name, tag_line, puuid, account_level, rank_tier, avatar_url, is_primary, status, last_synced_at, created_at, updated_at
        from player
        where id = #{playerId} and user_id = #{userId}
        limit 1
        """)
    Player selectByIdAndUserId(@Param("playerId") Long playerId, @Param("userId") Long userId);

    @Select("""
        select id, user_id, platform, region_code, game_name, tag_line, puuid, account_level, rank_tier, avatar_url, is_primary, status, last_synced_at, created_at, updated_at
        from player
        where user_id = #{userId} and is_primary = 1
        limit 1
        """)
    Player selectPrimaryByUserId(@Param("userId") Long userId);

    @Select("""
        select id, user_id, platform, region_code, game_name, tag_line, puuid, account_level, rank_tier, avatar_url, is_primary, status, last_synced_at, created_at, updated_at
        from player
        where user_id = #{userId}
        order by is_primary desc, id asc
        limit 1
        """)
    Player selectFirstByUserId(@Param("userId") Long userId);
}
