# Valorant Matches Assistant 后端说明

## 项目概览

这是一个基于 `Spring Boot 3`、`Spring Security`、`MyBatis-Plus`、`MySQL` 的后端项目，当前代码重点覆盖以下能力：

- 用户登录与 JWT 鉴权
- 当前登录用户信息查询
- 当前会话上下文查询
- 玩家比赛历史查询
- 玩家比赛详情查询
- 图片上传到阿里云 OSS
- 当前用户头像上传并回写数据库
- 上传本地图片并自动回写玩家头像
- 静态仪表盘页面访问

当前仓库里已经包含知识库、聊天、向量检索相关表结构和基础模块目录，但这些能力目前还没有开放成可直接调用的业务接口。

## 当前可用功能

### 1. 登录与鉴权

当前已实现：

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/me`
- `GET /api/v1/auth/session-context`

说明：

- 登录成功后会返回 `accessToken`
- 除登录接口、健康检查接口、仪表盘静态页外，其他接口默认都需要携带 `Bearer Token`
- JWT 过期时间由 `VALORANT_ASSISTANT_JWT_EXPIRATION_MINUTES` 控制

### 2. 玩家比赛数据

当前已实现：

- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`

说明：

- 只能查询当前登录用户自己名下的玩家数据
- 比赛历史支持分页
- 比赛详情会按 `playerId + matchId` 返回单场对局数据

### 3. 图片上传到阿里云 OSS

当前已实现：

- `POST /api/v1/media/images`

说明：

- 上传成功后返回 OSS 对象路径和最终图片地址
- 支持图片类型：`JPEG`、`PNG`、`WebP`、`GIF`、`AVIF`
- 默认最大文件大小受以下配置共同控制：
  - `VALORANT_ASSISTANT_UPLOAD_MAX_FILE_SIZE`
  - `VALORANT_ASSISTANT_UPLOAD_MAX_REQUEST_SIZE`
  - `VALORANT_ASSISTANT_OSS_MAX_FILE_SIZE`

### 4. 上传玩家头像并回写数据库

当前已实现：

- `POST /api/v1/players/{playerId}/avatar`

说明：

- 这个接口会把你上传的本地图片先传到 OSS
- 然后自动把返回的图片地址写回 `player.avatar_url`
- 只允许修改当前登录用户自己名下玩家的头像

### 5. 上传当前用户头像并回写数据库

当前已实现：

- `POST /api/v1/users/me/avatar`

说明：

- 这个接口会把当前登录用户上传的头像图片先传到 OSS
- 然后自动把返回的图片地址写回 `user_profile.avatar_url`
- 仪表盘登录卡片上的 `user_avatar` 会优先显示这个地址

### 6. 仪表盘静态页面

当前已实现：

- `GET /valorant-dashboard`

说明：

- 页面资源位于 `backend/src/main/resources/static/valorant-dashboard`
- 本地默认 logo 使用 `art/Logo Wallpapers/VALORANT_Logo_V_thumbnail.jpg` 的静态副本
- 未登录时，`Recent Matches`、`MATCH SNAPSHOT`、玩家信息等数据区域会进入模糊锁定态，并显示 `Log in to view`
- 如果配置了 `OSS` 基础地址，页面里的地图图、英雄图、默认头像会优先按 OSS 地址拼接展示

## 当前接口清单

### 无需登录即可访问

- `POST /api/v1/auth/login`
- `GET /actuator/health`
- `GET /actuator/info`
- `GET /valorant-dashboard`

### 需要登录后访问

- `GET /api/v1/auth/me`
- `GET /api/v1/auth/session-context`
- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`
- `POST /api/v1/media/images`
- `POST /api/v1/users/me/avatar`
- `POST /api/v1/players/{playerId}/avatar`

## 典型用法

### 1. 登录获取 Token

请求：

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"usernameOrEmail\":\"admin\",\"password\":\"Admin@123456\"}"
```

入参说明：

- `usernameOrEmail`：用户名或邮箱
- `password`：密码

### 2. 查询当前用户

```bash
curl http://localhost:8080/api/v1/auth/me \
  -H "Authorization: Bearer <token>"
```

### 3. 查询当前会话上下文

这个接口适合前端初始化时一次性拉取用户、主玩家、仪表盘资源地址：

```bash
curl http://localhost:8080/api/v1/auth/session-context \
  -H "Authorization: Bearer <token>"
```

### 4. 查询玩家比赛历史

```bash
curl "http://localhost:8080/api/v1/players/1/matches?page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

参数说明：

- `page`：页码，从 `0` 开始
- `size`：每页条数，范围 `1` 到 `100`

### 5. 查询玩家比赛详情

```bash
curl http://localhost:8080/api/v1/players/1/matches/1001 \
  -H "Authorization: Bearer <token>"
```

### 6. 上传图片到 OSS

```bash
curl -X POST http://localhost:8080/api/v1/media/images \
  -H "Authorization: Bearer <token>" \
  -F "file=@D:/images/avatar.png" \
  -F "folder=avatars"
```

参数说明：

- `file`：本地图片文件
- `folder`：可选，OSS 下的业务目录

返回值里通常会包含：

- `objectKey`
- `url`
- `originalFilename`
- `contentType`
- `size`

### 7. 上传本地图片并回写玩家头像

```bash
curl -X POST http://localhost:8080/api/v1/players/1/avatar \
  -H "Authorization: Bearer <token>" \
  -F "file=@D:/images/player-avatar.png"
```

这个接口成功后会完成两件事：

- 图片上传到 OSS
- 数据库 `player.avatar_url` 更新为 OSS 地址

### 8. 上传当前用户头像并回写 user_profile

```bash
curl -X POST http://localhost:8080/api/v1/users/me/avatar \
  -H "Authorization: Bearer <token>" \
  -F "file=@D:/images/user-avatar.png"
```

这个接口成功后会完成两件事：

- 图片上传到 OSS
- 数据库 `user_profile.avatar_url` 更新为 OSS 地址

## 环境变量说明

项目统一使用 `VALORANT_ASSISTANT_*` 前缀。

### 基础运行配置

- `VALORANT_ASSISTANT_API_PORT`
- `VALORANT_ASSISTANT_API_CONTEXT_PATH`
- `VALORANT_ASSISTANT_JWT_SECRET`
- `VALORANT_ASSISTANT_JWT_EXPIRATION_MINUTES`

### MySQL 配置

- `VALORANT_ASSISTANT_MYSQL_HOST`
- `VALORANT_ASSISTANT_MYSQL_PORT`
- `VALORANT_ASSISTANT_MYSQL_DATABASE`
- `VALORANT_ASSISTANT_MYSQL_USERNAME`
- `VALORANT_ASSISTANT_MYSQL_PASSWORD`

### OSS 配置

- `VALORANT_ASSISTANT_OSS_ENDPOINT`
- `VALORANT_ASSISTANT_OSS_BUCKET`
- `VALORANT_ASSISTANT_OSS_ACCESS_KEY_ID`
- `VALORANT_ASSISTANT_OSS_ACCESS_KEY_SECRET`
- `VALORANT_ASSISTANT_OSS_PUBLIC_BASE_URL`
- `VALORANT_ASSISTANT_OSS_ROOT_PATH`
- `VALORANT_ASSISTANT_OSS_MAX_FILE_SIZE`

推荐说明：

- `VALORANT_ASSISTANT_OSS_ENDPOINT` 例如：`https://oss-cn-hangzhou.aliyuncs.com`
- `VALORANT_ASSISTANT_OSS_PUBLIC_BASE_URL` 例如：`https://your-bucket.oss-cn-hangzhou.aliyuncs.com`
- 如果你接了 CDN，这里也可以直接填 CDN 域名

### 上传限制配置

- `VALORANT_ASSISTANT_UPLOAD_MAX_FILE_SIZE`
- `VALORANT_ASSISTANT_UPLOAD_MAX_REQUEST_SIZE`

### 默认管理员初始化

- `VALORANT_ASSISTANT_BOOTSTRAP_SEED_ADMIN`
- `VALORANT_ASSISTANT_BOOTSTRAP_ADMIN_USERNAME`
- `VALORANT_ASSISTANT_BOOTSTRAP_ADMIN_PASSWORD`

根目录变量示例见 [.env.example](/D:/valorant_matches_assistant/.env.example)。

## 数据库说明

初始化数据库前，请先执行 [schema.sql](/D:/valorant_matches_assistant/docs/database/schema.sql)。

当前和已开放功能直接相关的主要表有：

- `user`
- `user_profile`
- `player`
- `match_record`
- `player_match_stats`

当前头像上传回写使用的字段是：

- `player.avatar_url`

## 本地启动

### 方式一：命令行启动

```bash
cd backend
mvn spring-boot:run
```

### 方式二：IntelliJ IDEA

建议把 [backend](/D:/valorant_matches_assistant/backend/README.md) 目录作为项目根打开，而不是仓库根目录。

推荐设置：

- `JDK 17`
- 重新导入 [pom.xml](/D:/valorant_matches_assistant/backend/pom.xml)
- 使用共享运行配置 `ValorantAssistant Local`

项目中还提供了这些辅助文件：

- [settings.xml](/D:/valorant_matches_assistant/backend/settings.xml)：项目本地 Maven 配置
- [.mvn/maven.config](/D:/valorant_matches_assistant/backend/.mvn/maven.config)：让 Maven 默认优先使用项目配置
- [application-local.yml](/D:/valorant_matches_assistant/backend/src/main/resources/application-local.yml)：本地开发配置

## 当前未开放的能力

下面这些内容目前在仓库中有表结构、目录或预留设计，但还不能算作已开放功能：

- 知识库文档上传与解析流程
- Embedding 与向量库入库
- RAG 检索问答
- 聊天会话与聊天消息业务接口

文档中不应把这些内容写成“已可用”，除非对应接口和调用方式已经真正落地。

## 文档维护约定

这个 README 现在按“当前代码真实可用能力”维护，后续如果新增、删除或修改以下内容，需要同步更新本文件：

- 新增或删除接口
- 鉴权规则变化
- 返回字段或调用方式变化
- 环境变量变化
- 图片上传、头像回写、仪表盘资源加载规则变化

维护原则：

- 已写进 README 的内容应当可以直接按文档操作
- 还没实现的内容不要写成已支持
- 示例命令优先保持可复制、可执行
