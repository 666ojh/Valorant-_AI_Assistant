package com.valorantassistant.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.valorantassistant.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("""
        select id, username, email, phone, password_hash, role_code, status, last_login_at, last_login_ip, created_at, updated_at, deleted_at
        from `user`
        where username = #{username}
        limit 1
        """)
    User selectByUsername(@Param("username") String username);

    @Select("""
        select id, username, email, phone, password_hash, role_code, status, last_login_at, last_login_ip, created_at, updated_at, deleted_at
        from `user`
        where username = #{identifier} or email = #{identifier}
        limit 1
        """)
    User selectByUsernameOrEmail(@Param("identifier") String identifier);

    @Select("""
        select count(1) > 0
        from `user`
        where username = #{username}
        """)
    boolean existsByUsername(@Param("username") String username);
}
