package com.valorantassistant.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.valorantassistant.user.domain.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfile> {

    @Select("""
        select user_id, display_name, avatar_url, region_code, timezone, preferred_language, bio, created_at, updated_at
        from user_profile
        where user_id = #{userId}
        limit 1
        """)
    UserProfile selectByUserId(@Param("userId") Long userId);
}
