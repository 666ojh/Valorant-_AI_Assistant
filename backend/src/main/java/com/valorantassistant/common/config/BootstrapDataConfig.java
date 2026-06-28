package com.valorantassistant.common.config;

import com.valorantassistant.user.domain.User;
import com.valorantassistant.user.domain.UserProfile;
import com.valorantassistant.user.domain.UserStatus;
import com.valorantassistant.user.mapper.UserMapper;
import com.valorantassistant.user.mapper.UserProfileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BootstrapDataConfig {

    @Bean
    public CommandLineRunner seedAdminUser(
        UserMapper userMapper,
        UserProfileMapper userProfileMapper,
        PasswordEncoder passwordEncoder,
        @Value("${app.bootstrap.seed-admin}") boolean seedAdmin,
        @Value("${app.bootstrap.admin-username}") String adminUsername,
        @Value("${app.bootstrap.admin-password}") String adminPassword
    ) {
        return args -> {
            if (!seedAdmin || userMapper.existsByUsername(adminUsername)) {
                return;
            }

            User user = new User();
            user.setUsername(adminUsername);
            user.setEmail("admin@valorant-assistant.local");
            user.setPasswordHash(passwordEncoder.encode(adminPassword));
            user.setRoleCode("ADMIN");
            user.setStatus(UserStatus.ACTIVE);

            userMapper.insert(user);

            UserProfile profile = new UserProfile();
            profile.setUserId(user.getId());
            profile.setDisplayName("System Admin");
            profile.setPreferredLanguage("zh-CN");
            userProfileMapper.insert(profile);
        };
    }
}
