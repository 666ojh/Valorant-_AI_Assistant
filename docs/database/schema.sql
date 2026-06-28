CREATE DATABASE IF NOT EXISTS `valorant_assistant`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE `valorant_assistant`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `email` VARCHAR(128) NULL,
  `phone` VARCHAR(32) NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `role_code` VARCHAR(32) NOT NULL DEFAULT 'USER',
  `status` VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
  `last_login_at` DATETIME(3) NULL,
  `last_login_ip` VARCHAR(64) NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `deleted_at` DATETIME(3) NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_username` (`username`),
  UNIQUE KEY `uk_user_email` (`email`),
  UNIQUE KEY `uk_user_phone` (`phone`),
  KEY `idx_user_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user_profile` (
  `user_id` BIGINT UNSIGNED NOT NULL,
  `display_name` VARCHAR(64) NULL,
  `avatar_url` VARCHAR(255) NULL,
  `region_code` VARCHAR(32) NULL,
  `timezone` VARCHAR(64) NULL,
  `preferred_language` VARCHAR(16) NULL,
  `bio` VARCHAR(255) NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_profile_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `player` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NULL,
  `platform` VARCHAR(32) NOT NULL DEFAULT 'RIOT',
  `region_code` VARCHAR(32) NOT NULL,
  `game_name` VARCHAR(64) NOT NULL,
  `tag_line` VARCHAR(16) NOT NULL,
  `puuid` VARCHAR(128) NOT NULL,
  `account_level` INT UNSIGNED NULL,
  `rank_tier` VARCHAR(32) NULL,
  `avatar_url` VARCHAR(255) NULL,
  `is_primary` TINYINT(1) NOT NULL DEFAULT 0,
  `status` VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
  `last_synced_at` DATETIME(3) NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_player_puuid` (`puuid`),
  UNIQUE KEY `uk_player_riot_id` (`platform`, `region_code`, `game_name`, `tag_line`),
  KEY `idx_player_user` (`user_id`, `is_primary`),
  KEY `idx_player_status` (`status`),
  CONSTRAINT `fk_player_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `match_record` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `match_code` VARCHAR(64) NOT NULL,
  `platform` VARCHAR(32) NOT NULL DEFAULT 'RIOT',
  `region_code` VARCHAR(32) NOT NULL,
  `season_code` VARCHAR(32) NULL,
  `queue_code` VARCHAR(32) NULL,
  `mode_code` VARCHAR(32) NOT NULL,
  `map_name` VARCHAR(64) NOT NULL,
  `cluster_code` VARCHAR(32) NULL,
  `started_at` DATETIME(3) NOT NULL,
  `ended_at` DATETIME(3) NULL,
  `duration_seconds` INT UNSIGNED NULL,
  `winning_team` VARCHAR(16) NULL,
  `red_score` INT UNSIGNED NULL,
  `blue_score` INT UNSIGNED NULL,
  `rounds_played` INT UNSIGNED NULL,
  `patch_version` VARCHAR(32) NULL,
  `raw_payload` JSON NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_match_record_code` (`match_code`),
  KEY `idx_match_record_started_at` (`started_at`),
  KEY `idx_match_record_map_mode` (`map_name`, `mode_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `player_match_stats` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `player_id` BIGINT UNSIGNED NOT NULL,
  `match_record_id` BIGINT UNSIGNED NOT NULL,
  `team_code` VARCHAR(16) NOT NULL,
  `agent_code` VARCHAR(32) NOT NULL,
  `party_id` VARCHAR(64) NULL,
  `won` TINYINT(1) NOT NULL DEFAULT 0,
  `kills` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  `deaths` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  `assists` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  `average_combat_score` INT UNSIGNED NULL,
  `damage_dealt` INT UNSIGNED NULL,
  `damage_received` INT UNSIGNED NULL,
  `headshot_rate` DECIMAL(5,2) NULL,
  `first_kills` SMALLINT UNSIGNED NULL,
  `first_deaths` SMALLINT UNSIGNED NULL,
  `plants` SMALLINT UNSIGNED NULL,
  `defuses` SMALLINT UNSIGNED NULL,
  `econ_rating` INT UNSIGNED NULL,
  `kast_rate` DECIMAL(5,2) NULL,
  `rank_tier` VARCHAR(32) NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_player_match_stats_player_match` (`player_id`, `match_record_id`),
  KEY `idx_player_match_stats_match` (`match_record_id`),
  KEY `idx_player_match_stats_player` (`player_id`),
  CONSTRAINT `fk_player_match_stats_player`
    FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `fk_player_match_stats_match_record`
    FOREIGN KEY (`match_record_id`) REFERENCES `match_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `knowledge_document` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `document_type` VARCHAR(32) NOT NULL,
  `source_type` VARCHAR(32) NOT NULL DEFAULT 'UPLOAD',
  `source_url` VARCHAR(512) NULL,
  `version_label` VARCHAR(64) NULL,
  `language_code` VARCHAR(16) NULL,
  `minio_bucket` VARCHAR(128) NOT NULL,
  `minio_object_key` VARCHAR(255) NOT NULL,
  `original_file_name` VARCHAR(255) NOT NULL,
  `content_hash` VARCHAR(128) NULL,
  `file_size_bytes` BIGINT UNSIGNED NULL,
  `parse_status` VARCHAR(32) NOT NULL DEFAULT 'UPLOADED',
  `summary` TEXT NULL,
  `uploaded_by` BIGINT UNSIGNED NULL,
  `uploaded_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `parsed_at` DATETIME(3) NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_knowledge_document_object` (`minio_bucket`, `minio_object_key`),
  KEY `idx_knowledge_document_status` (`parse_status`),
  KEY `idx_knowledge_document_uploaded_by` (`uploaded_by`),
  CONSTRAINT `fk_knowledge_document_uploaded_by`
    FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `chat_session` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT UNSIGNED NOT NULL,
  `player_id` BIGINT UNSIGNED NULL,
  `session_name` VARCHAR(128) NOT NULL,
  `session_type` VARCHAR(32) NOT NULL DEFAULT 'GENERAL',
  `status` VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
  `last_message_at` DATETIME(3) NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  KEY `idx_chat_session_user` (`user_id`, `last_message_at`),
  KEY `idx_chat_session_player` (`player_id`),
  CONSTRAINT `fk_chat_session_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_chat_session_player`
    FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `chat_message` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `chat_session_id` BIGINT UNSIGNED NOT NULL,
  `sender_type` VARCHAR(32) NOT NULL,
  `message_type` VARCHAR(32) NOT NULL DEFAULT 'TEXT',
  `content_text` TEXT NOT NULL,
  `token_count` INT UNSIGNED NULL,
  `knowledge_document_id` BIGINT UNSIGNED NULL,
  `cited_match_record_id` BIGINT UNSIGNED NULL,
  `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  KEY `idx_chat_message_session` (`chat_session_id`, `created_at`),
  KEY `idx_chat_message_document` (`knowledge_document_id`),
  KEY `idx_chat_message_match` (`cited_match_record_id`),
  CONSTRAINT `fk_chat_message_session`
    FOREIGN KEY (`chat_session_id`) REFERENCES `chat_session` (`id`),
  CONSTRAINT `fk_chat_message_document`
    FOREIGN KEY (`knowledge_document_id`) REFERENCES `knowledge_document` (`id`),
  CONSTRAINT `fk_chat_message_match_record`
    FOREIGN KEY (`cited_match_record_id`) REFERENCES `match_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
