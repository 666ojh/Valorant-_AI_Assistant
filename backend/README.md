# Valorant Matches Assistant Backend

## 项目概览

这是一个基于 `Spring Boot 3`、`Spring Security`、`MyBatis-Plus`、`MySQL` 的后端项目，当前已经提供：

- 用户登录
- 用户注册
- JWT 鉴权
- 当前用户与会话上下文查询
- 玩家比赛历史查询
- 玩家单场比赛详情查询
- 图片上传到 OSS
- 当前用户头像上传并回写数据库
- 玩家头像上传并回写数据库
- 静态仪表盘页面
- 管理员用户总览页面数据接口

当前仓库里还包含知识库、聊天、向量检索相关表结构和目录，但这些能力目前还没有开放成可直接调用的业务接口。

## 当前可用功能

### 1. 认证与会话

当前已实现：

- `POST /api/v1/auth/login`
- `POST /api/v1/auth/register`
- `GET /api/v1/auth/me`
- `GET /api/v1/auth/session-context`

说明：

- 登录或注册成功后都会返回 `accessToken`
- 除登录、注册、健康检查、仪表盘静态资源外，其余接口默认都需要 `Bearer Token`
- JWT 过期时间由 `VALORANT_ASSISTANT_JWT_EXPIRATION_MINUTES` 控制

### 2. 玩家比赛数据

当前已实现：

- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`
- `GET /api/v1/players/me`
- `POST /api/v1/players/{playerId}/activate`

说明：

- 只能查询当前登录用户自己名下绑定的玩家数据
- 比赛历史支持分页
- 比赛详情会按 `playerId + matchId` 返回单场对局数据
- 可以查询当前登录用户已绑定的全部游戏账号
- 可以把某个已绑定游戏账号切换为当前主账号

### 3. 媒体上传

当前已实现：

- `POST /api/v1/media/images`
- `POST /api/v1/users/me/avatar`
- `POST /api/v1/players/{playerId}/avatar`

说明：

- 图片会先上传到 OSS
- 用户头像会回写到 `user_profile.avatar_url`
- 玩家头像会回写到 `player.avatar_url`
- 支持 `JPEG`、`PNG`、`WebP`、`GIF`、`AVIF`

### 4. 管理员总览

当前已实现：

- `GET /api/v1/admin/users/overview`

说明：

- 仅 `ADMIN` 角色可访问
- 返回所有用户的基础信息
- 返回每个用户名下绑定的游戏账号
- 返回每个游戏账号的聚合数据，例如：
  - `matchCount`
  - `winRate`
  - `averageAcs`
  - `kdRatio`
  - `averageKills`
  - `averageDeaths`
  - `averageAssists`
  - `headshotRate`
  - `kastRate`
- 不返回逐场比赛明细

### 5. 仪表盘前端

当前已实现：

- `GET /valorant-dashboard`

说明：

- 页面资源位于 `backend/src/main/resources/static/valorant-dashboard`
- 支持登录、注册、当前用户仪表盘
- 当管理员登录后，页面会额外显示管理员总览面板
- 管理员面板展示的是用户与账号汇总数据，不会展开到单局比赛细节

## 接口清单

### 无需登录

- `POST /api/v1/auth/login`
- `POST /api/v1/auth/register`
- `GET /actuator/health`
- `GET /actuator/info`
- `GET /valorant-dashboard`

### 需要登录

- `GET /api/v1/auth/me`
- `GET /api/v1/auth/session-context`
- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`
- `GET /api/v1/players/me`
- `POST /api/v1/media/images`
- `POST /api/v1/users/me/avatar`
- `POST /api/v1/players/{playerId}/avatar`
- `POST /api/v1/players/{playerId}/activate`

### 需要管理员权限

- `GET /api/v1/admin/users/overview`

## 典型用法

### 1. 注册

适合在 Apifox 中创建一个 `POST` 请求，直接粘贴下面参数：

```text
Method:
POST

Path:
/api/v1/auth/register

Headers:
Content-Type: application/json

Body (JSON):
{
  "username": "player01",
  "email": "player01@example.com",
  "displayName": "Player 01",
  "password": "Password@123"
}
```

### 2. 登录获取 Token

```text
Method:
POST

Path:
/api/v1/auth/login

Headers:
Content-Type: application/json

Body (JSON):
{
  "usernameOrEmail": "admin",
  "password": "Admin@123456"
}
```

### 3. 查询当前用户

```text
Method:
GET

Path:
/api/v1/auth/me

Headers:
Authorization: Bearer <token>
```

### 4. 查询会话上下文

适合前端初始化时一次性拉取用户、主玩家、仪表盘资源地址：

```text
Method:
GET

Path:
/api/v1/auth/session-context

Headers:
Authorization: Bearer <token>
```

### 5. 查询玩家比赛历史

```text
Method:
GET

Path:
/api/v1/players/1/matches

Headers:
Authorization: Bearer <token>

Query:
page=0
size=20
```

参数说明：

- `page`：页码，从 `0` 开始
- `size`：每页条数，范围 `1` 到 `100`

### 6. 查询玩家单场比赛详情

```text
Method:
GET

Path:
/api/v1/players/1/matches/1001

Headers:
Authorization: Bearer <token>
```

### 7. 查询当前用户绑定的全部游戏账号

```text
Method:
GET

Path:
/api/v1/players/me

Headers:
Authorization: Bearer <token>
```

### 8. 切换当前主游戏账号

```text
Method:
POST

Path:
/api/v1/players/2/activate

Headers:
Authorization: Bearer <token>
```

### 9. 上传图片到 OSS

```text
Method:
POST

Path:
/api/v1/media/images

Headers:
Authorization: Bearer <token>
Content-Type: multipart/form-data

Body (form-data):
file = <选择本地文件，类型设为 File>
folder = avatars
```

### 10. 上传当前用户头像

```text
Method:
POST

Path:
/api/v1/users/me/avatar

Headers:
Authorization: Bearer <token>
Content-Type: multipart/form-data

Body (form-data):
file = <选择本地文件，类型设为 File>
```

### 11. 上传玩家头像

```text
Method:
POST

Path:
/api/v1/players/1/avatar

Headers:
Authorization: Bearer <token>
Content-Type: multipart/form-data

Body (form-data):
file = <选择本地文件，类型设为 File>
```

### 12. 查询管理员用户总览

```text
Method:
GET

Path:
/api/v1/admin/users/overview

Headers:
Authorization: Bearer <admin-token>
```

## 默认管理员

项目启动时支持自动初始化管理员账号：

- 用户名：`admin`
- 密码：`Admin@123456`

相关配置：

- `VALORANT_ASSISTANT_BOOTSTRAP_SEED_ADMIN`
- `VALORANT_ASSISTANT_BOOTSTRAP_ADMIN_USERNAME`
- `VALORANT_ASSISTANT_BOOTSTRAP_ADMIN_PASSWORD`

如果数据库里已经存在同名用户，则不会重复创建。

## 环境变量说明

项目统一使用 `VALORANT_ASSISTANT_*` 前缀。

### 基础运行配置

- `VALORANT_ASSISTANT_API_PORT`
- `VALORANT_ASSISTANT_API_CONTEXT_PATH`
- `VALORANT_ASSISTANT_JWT_SECRET`
- `VALORANT_ASSISTANT_JWT_EXPIRATION_MINUTES`

### MySQL

- `VALORANT_ASSISTANT_MYSQL_HOST`
- `VALORANT_ASSISTANT_MYSQL_PORT`
- `VALORANT_ASSISTANT_MYSQL_DATABASE`
- `VALORANT_ASSISTANT_MYSQL_USERNAME`
- `VALORANT_ASSISTANT_MYSQL_PASSWORD`

### OSS

- `VALORANT_ASSISTANT_OSS_ENDPOINT`
- `VALORANT_ASSISTANT_OSS_BUCKET`
- `VALORANT_ASSISTANT_OSS_ACCESS_KEY_ID`
- `VALORANT_ASSISTANT_OSS_ACCESS_KEY_SECRET`
- `VALORANT_ASSISTANT_OSS_PUBLIC_BASE_URL`
- `VALORANT_ASSISTANT_OSS_ROOT_PATH`
- `VALORANT_ASSISTANT_OSS_MAX_FILE_SIZE`

### 上传限制

- `VALORANT_ASSISTANT_UPLOAD_MAX_FILE_SIZE`
- `VALORANT_ASSISTANT_UPLOAD_MAX_REQUEST_SIZE`

### 管理员初始化

- `VALORANT_ASSISTANT_BOOTSTRAP_SEED_ADMIN`
- `VALORANT_ASSISTANT_BOOTSTRAP_ADMIN_USERNAME`
- `VALORANT_ASSISTANT_BOOTSTRAP_ADMIN_PASSWORD`

示例见 [.env.example](/D:/valorant_matches_assistant/.env.example)。

说明：

- `.env.example` 里还保留了 `Redis`、`MinIO`、`Milvus` 等预留变量
- 当前这几个能力还没有接入到本 README 所列出的已开放接口中

## 数据库

初始化数据库前，请先执行 [schema.sql](/D:/valorant_matches_assistant/docs/database/schema.sql)。

当前和已开放功能直接相关的主要表有：

- `user`
- `user_profile`
- `player`
- `match_record`
- `player_match_stats`

## 本地启动

### 方式一：命令行

```bash
cd backend
mvn spring-boot:run
```

### 方式二：IntelliJ IDEA

建议把 [backend](/D:/valorant_matches_assistant/backend/README.md) 目录作为项目根打开。

推荐设置：

- `JDK 17`
- 重新导入 [pom.xml](/D:/valorant_matches_assistant/backend/pom.xml)
- 使用共享运行配置 `ValorantAssistant Local`

项目内还有这些辅助文件：

- [settings.xml](/D:/valorant_matches_assistant/backend/settings.xml)
- [application-local.yml](/D:/valorant_matches_assistant/backend/src/main/resources/application-local.yml)

## 当前未开放的能力

下面这些内容在仓库中有表结构、目录或预留设计，但目前还不能视为已开放功能：

- 知识库文档上传与解析流程
- Embedding 与向量库写入
- RAG 检索问答
- 聊天会话与聊天消息业务接口

## 文档维护约定

后续如果出现以下变化，请同步更新本文件：

- 新增或删除接口
- 角色权限规则变化
- 返回字段变化
- 环境变量变化
- 仪表盘登录、注册、管理员视图行为变化

维护原则：

- README 只描述当前真实可用能力
- 未实现的内容不要写成已支持
- 示例命令尽量保持可直接复制执行
