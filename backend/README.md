# Backend MVP

## Current Scope

This backend scaffold is intentionally optimized for the first runnable version:

- user login
- player binding and lookup foundation
- match history query
- match detail query
- knowledge/chat tables reserved for the next phase

## Persistence Stack

The backend now uses `Mapper + MyBatis-Plus` as the only persistence approach.
The earlier JPA repository experiment has been removed from the active code path.

## Module Layout

```text
backend/
├── auth
├── chat
├── common
├── knowledge
├── match
├── player
└── user
```

## Port and Environment Naming

The project uses the `VALORANT_ASSISTANT_*` prefix so every local service port is easy to identify:

- `VALORANT_ASSISTANT_API_PORT`
- `VALORANT_ASSISTANT_MYSQL_PORT`
- `VALORANT_ASSISTANT_REDIS_PORT`
- `VALORANT_ASSISTANT_MINIO_API_PORT`
- `VALORANT_ASSISTANT_MINIO_CONSOLE_PORT`
- `VALORANT_ASSISTANT_MILVUS_PORT`

See the root [.env.example](/D:/valorant_matches_assistant/.env.example).

## Planned MVP APIs

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/me`
- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`

## Local Start

```bash
cd backend
mvn spring-boot:run
```

Before starting, create the MySQL schema with [schema.sql](/D:/valorant_matches_assistant/docs/database/schema.sql).

## IntelliJ IDEA

Open [backend](/D:/valorant_matches_assistant/backend/README.md) as the project root, not the repository root.

- Project SDK: `JDK 17`
- Language level: `SDK default` or `17`
- Reimport the Maven project from [pom.xml](/D:/valorant_matches_assistant/backend/pom.xml)
- Use the shared run config `ValorantAssistant Local`

The project also includes:

- [settings.xml](/D:/valorant_matches_assistant/backend/settings.xml) for project-local Maven repo settings
- [.mvn/maven.config](/D:/valorant_matches_assistant/backend/.mvn/maven.config) so Maven commands prefer that settings file
- [application-local.yml](/D:/valorant_matches_assistant/backend/src/main/resources/application-local.yml) so IDEA can start against local MySQL by default
