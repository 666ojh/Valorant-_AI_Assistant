# Valorant Matches Assistant 项目零基础讲解

这份文档假设你对 `Java`、`Spring Boot`、`Spring Security`、`MyBatis-Plus`、`MySQL` 都几乎不了解，也还没有看过这个项目的代码。

目标不是把名词堆给你，而是帮你建立一套能真正看懂这个项目的脑图：

1. 这个项目现在到底能做什么。
2. 一次请求在项目里是怎么流动的。
3. 每个目录、每个模块、每类代码各自负责什么。
4. 这个项目用到了哪些 Java 和 Spring Boot 基础知识。
5. 哪些是已经实现的功能，哪些只是为未来预留的结构。

---

## 1. 先用一句话理解这个项目

这是一个 `VALORANT 战绩助手` 的后端项目，当前主要提供这些能力：

- 用户登录
- JWT 身份认证
- 查询当前登录用户信息
- 查询当前登录用户绑定的主玩家信息
- 查询玩家比赛历史和比赛详情
- 上传图片到阿里云 OSS
- 上传用户头像并回写数据库
- 上传玩家头像并回写数据库
- 提供一个静态仪表盘页面 `valorant-dashboard`

它还包含了一些未来功能的表结构和目录，比如：

- 知识库文档
- 聊天会话
- AI / RAG 相关能力

但这些未来能力目前还没有对外开放成真正可调用的业务接口。

---

## 2. 如果你完全不懂 Java，先掌握这些最小概念

你不需要先学完整门 Java，只要先认识这几个概念，就能开始看这个项目。

### 2.1 类（Class）

Java 的主要组织单位叫“类”。

比如：

```java
public class AuthService {
}
```

你可以把类理解成一种“模板”或者“角色定义”。

在这个项目里：

- `AuthController` 负责接收认证相关的 HTTP 请求
- `AuthService` 负责认证业务逻辑
- `User` 负责描述用户数据长什么样

### 2.2 对象（Object）

类是图纸，对象是按图纸造出来的实例。

比如数据库里有一条用户记录，代码里就可能有一个 `User` 对象来表示它。

### 2.3 方法（Method）

方法就是类里的“动作”。

例如：

```java
public LoginResponse login(LoginRequest request, String clientIp) {
}
```

这个方法表示“执行登录逻辑”。

### 2.4 字段（Field）

字段就是类里的“数据”。

例如 `User` 类中的：

- `username`
- `email`
- `passwordHash`

这些字段基本对应数据库表里的列。

### 2.5 接口（Interface）

接口可以理解成“只规定能力，不写具体实现”的约定。

在这个项目里最常见的是：

- `Mapper` 继承框架提供的接口

例如：

```java
public interface UserMapper extends BaseMapper<User> {
}
```

### 2.6 枚举（Enum）

枚举表示“固定的一组可选值”。

例如 [backend/src/main/java/com/valorantassistant/user/domain/UserStatus.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/user/domain/UserStatus.java)：

- `ACTIVE`
- `LOCKED`
- `DISABLED`

这表示用户状态只能是这几种。

### 2.7 继承（extends）

继承表示“复用父类的公共内容”。

例如 `User`、`Player`、`MatchRecord` 都继承了 `BaseEntity`，于是它们自动拥有：

- `createdAt`
- `updatedAt`

这能避免每个类都重复写一遍。

### 2.8 注解（Annotation）

注解就是贴在类、方法、字段上的“标签”，框架会根据标签做事情。

比如：

- `@RestController`：这是一个接口控制器
- `@Service`：这是业务服务类
- `@Configuration`：这是配置类
- `@GetMapping`：这个方法处理 GET 请求
- `@PostMapping`：这个方法处理 POST 请求

这个项目大量依赖注解，所以看懂注解非常重要。

### 2.9 record

Java 里 `record` 是一种专门拿来装数据的轻量类型。

比如 [backend/src/main/java/com/valorantassistant/auth/dto/LoginResponse.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/dto/LoginResponse.java)：

```java
public record LoginResponse(
    String accessToken,
    Long userId,
    String username,
    String roleCode
) {
}
```

你可以把它理解成“专门给前端返回结果的小数据盒子”。

---

## 3. 如果你完全不懂 Spring Boot，先掌握这些最小概念

### 3.1 Spring Boot 是什么

`Spring Boot` 是一个帮助你快速写 Java Web 后端的框架。

它帮你做好了很多基础设施，比如：

- 启动 Web 服务
- 接收 HTTP 请求
- 管理对象创建
- 读取配置文件
- 集成安全、数据库、校验等组件

### 3.2 什么是“后端启动类”

[backend/src/main/java/com/valorantassistant/ValorantAssistantApplication.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/ValorantAssistantApplication.java) 是项目入口：

```java
@SpringBootApplication
public class ValorantAssistantApplication {
    public static void main(String[] args) {
        SpringApplication.run(ValorantAssistantApplication.class, args);
    }
}
```

含义：

- `main` 是 Java 程序入口
- `SpringApplication.run(...)` 会启动整个 Spring Boot 应用
- `@SpringBootApplication` 会告诉 Spring：“从这里开始扫描并装配整个项目”

### 3.3 Bean 是什么

在 Spring 里，很多对象不是你手动 `new` 出来的，而是由 Spring 自动创建和管理。

这些被 Spring 管理的对象通常叫 `Bean`。

例如：

- `AuthController`
- `AuthService`
- `JwtTokenProvider`

### 3.4 依赖注入（Dependency Injection）

依赖注入可以理解成：

“一个类需要什么对象，不自己造，而是由 Spring 自动塞给它。”

比如：

```java
public AuthController(AuthService authService) {
    this.authService = authService;
}
```

意思是：

- `AuthController` 需要 `AuthService`
- Spring 会自动创建 `AuthService`
- 再把它传给 `AuthController`

### 3.5 Controller / Service / Mapper 分层

这是这个项目最重要的结构。

#### Controller

负责直接接收 HTTP 请求、返回 HTTP 响应。

例如：

- `AuthController`
- `MatchController`
- `MediaController`

#### Service

负责业务逻辑。

例如：

- 登录时如何校验账号密码
- 查询比赛前如何检查“这个玩家是不是当前用户自己的”
- 上传头像后如何把 OSS 地址写回数据库

#### Mapper

负责访问数据库。

例如：

- 查用户
- 查玩家
- 查比赛历史

所以你可以记成：

`Controller` 接请求  
`Service` 处理规则  
`Mapper` 查数据库

---

## 4. 这个项目的技术栈，按“你能看懂代码”的方式解释

看 [backend/pom.xml](/D:/valorant_matches_assistant/backend/pom.xml) 可以知道它用的主要依赖。

### 4.1 Spring Boot Web

作用：

- 让项目能提供 HTTP 接口
- 支持 `@RestController`、`@GetMapping`、`@PostMapping`

没有它，这个项目就不能当后端 API 服务运行。

### 4.2 Spring Security

作用：

- 保护接口
- 判断哪些接口能匿名访问，哪些必须登录
- 处理认证上下文

这个项目里它和 JWT 配合使用。

### 4.3 MyBatis-Plus

作用：

- 连接数据库
- 把 Java 对象映射到数据库表
- 提供一些现成的 CRUD 能力
- 提供分页能力

你可以把它理解成“帮 Java 更方便地查 MySQL 的工具”。

### 4.4 MySQL 驱动

作用：

- 让 Java 程序可以连接 MySQL 数据库

### 4.5 JJWT

作用：

- 生成 JWT
- 解析 JWT
- 校验 JWT 是否有效

### 4.6 阿里云 OSS SDK

作用：

- 上传图片到阿里云对象存储 OSS

### 4.7 Actuator

作用：

- 提供健康检查接口

目前开放了：

- `/actuator/health`
- `/actuator/info`

---

## 5. 项目目录结构怎么读

仓库顶层大致是这样：

- `backend`：后端代码
- `docs`：文档
- `art`：一些设计素材
- `.env.example`：环境变量示例

### 5.1 backend 目录

`backend` 是真正的 Spring Boot 项目。

它里面最重要的路径有：

- `src/main/java`：Java 源代码
- `src/main/resources`：配置文件、静态资源
- `pom.xml`：Maven 依赖配置

### 5.2 Java 包结构

`src/main/java/com/valorantassistant` 下的模块可以这样理解：

- `auth`：认证、登录、当前会话
- `common`：全局通用配置和公共能力
- `media`：图片上传
- `match`：比赛历史和比赛详情
- `player`：玩家信息与玩家头像
- `user`：用户信息与用户头像
- `knowledge`：知识库文档实体，当前只有领域对象
- `chat`：聊天实体，当前没有接口和服务

### 5.3 resources 目录

- `application.yml`：主配置文件
- `application-local.yml`：本地开发覆盖配置
- `static/valorant-dashboard`：静态仪表盘页面

---

## 6. 当前已经实现的功能和未实现的边界

这一点非常重要，因为你以后看代码时，最容易被“目录存在”误导成“功能已完成”。

### 6.1 当前已经实现的功能

- 用户登录
- 登录后获取当前用户信息
- 登录后获取当前会话上下文
- 查询当前用户名下玩家的比赛历史
- 查询当前用户名下玩家的比赛详情
- 上传图片到 OSS
- 上传当前用户头像
- 上传某个玩家头像
- 访问静态仪表盘页面
- 项目启动时自动初始化管理员账号

### 6.2 当前还没有真正实现成可用接口的部分

虽然数据库或目录已经有这些内容，但它们目前还不是完整功能：

- 知识库文档上传与解析
- 向量化、Embedding、Milvus
- RAG 检索问答
- 聊天会话接口
- 聊天消息接口

也就是说，这个项目目前更准确地说是：

“一个以登录、玩家、比赛、上传、静态展示为核心的后端 MVP”

---

## 7. 一次请求在这个项目里是怎么走的

这是理解项目最关键的一张脑图。

### 7.1 先看总流程

一个典型请求大致会按这个顺序流动：

1. 浏览器或 `curl` 发请求给后端
2. Spring Security 先判断这个接口是否需要登录
3. 如果需要登录，`JwtAuthenticationFilter` 会尝试解析请求头里的 Token
4. 认证通过后，请求进入 Controller
5. Controller 调用 Service
6. Service 调用 Mapper 查数据库，或者调用其他 Service
7. Service 把结果组装成 DTO
8. Controller 用 `ApiResponse.success(...)` 统一返回

### 7.2 用“查比赛历史”举例

请求：

```text
GET /api/v1/players/{playerId}/matches?page=0&size=20
```

经过的主要类：

1. `SecurityConfig` 判断这个接口需要认证
2. `JwtAuthenticationFilter` 从 `Authorization: Bearer xxx` 里解析 Token
3. `CustomUserDetailsService` 加载用户
4. `MatchController#getMatchHistory(...)`
5. `MatchQueryService#getMatchHistory(...)`
6. `PlayerMapper` 检查这个玩家是否属于当前登录用户
7. `PlayerMatchStatsMapper#selectHistoryByPlayerId(...)` 查比赛数据
8. 返回 `MatchHistoryResponse`

所以整个系统不是“前端直接查数据库”，而是“一层一层地走”。

---

## 8. 公共层 common：这是项目的基础设施

`common` 模块不直接承载某个业务，而是为整个项目提供公共基础能力。

### 8.1 ApiResponse：统一返回格式

[backend/src/main/java/com/valorantassistant/common/api/ApiResponse.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/api/ApiResponse.java)

它定义了统一返回结构：

- `success`
- `code`
- `message`
- `data`

成功时：

```json
{
  "success": true,
  "code": "OK",
  "message": "success",
  "data": {}
}
```

失败时：

```json
{
  "success": false,
  "code": "UNAUTHORIZED",
  "message": "Authentication is required",
  "data": null
}
```

统一格式的好处是前端更容易处理。

### 8.2 GlobalExceptionHandler：统一异常处理

[backend/src/main/java/com/valorantassistant/common/api/GlobalExceptionHandler.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/api/GlobalExceptionHandler.java)

作用：

- 把参数校验异常转成统一的 400 响应
- 把 `ResponseStatusException` 转成统一格式
- 把其他未处理异常转成 500

你可以把它理解成“全局兜底错误翻译器”。

### 8.3 BaseEntity：公共基础字段

[backend/src/main/java/com/valorantassistant/common/domain/BaseEntity.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/domain/BaseEntity.java)

它提供：

- `createdAt`
- `updatedAt`

多个实体复用这两个字段。

### 8.4 MybatisPlusConfig：分页和自动填充

[backend/src/main/java/com/valorantassistant/common/config/MybatisPlusConfig.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/MybatisPlusConfig.java)

它做了两件事：

#### 分页插件

`PaginationInnerInterceptor` 让 MyBatis-Plus 支持分页查询。

#### 自动填充时间字段

插入数据时自动填：

- `createdAt`
- `updatedAt`

更新数据时自动更新：

- `updatedAt`

这就是为什么很多插入代码里没有手动给这两个字段赋值。

### 8.5 DashboardPageController：仪表盘页面入口

[backend/src/main/java/com/valorantassistant/common/config/DashboardPageController.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/DashboardPageController.java)

作用：

- 把 `/valorant-dashboard` 转发到静态页面 `index.html`

### 8.6 DashboardAssetsProperties 和 DashboardAssetService

这两个类负责“仪表盘里用到的图片资源地址”。

它们会从配置里读取：

- logo 路径
- 默认头像路径
- 地图图片路径
- 英雄图片路径

然后根据配置决定：

- 返回本地静态资源地址
- 或者拼成 OSS 公网地址

### 8.7 BootstrapDataConfig：启动时初始化管理员

[backend/src/main/java/com/valorantassistant/common/config/BootstrapDataConfig.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/BootstrapDataConfig.java)

这是一个非常实用的类。

项目启动时，如果配置允许，且数据库里还没有管理员账号，它会自动：

1. 创建一个 `user`
2. 给它设置加密后的密码
3. 创建对应的 `user_profile`

这依赖 `CommandLineRunner`，它的含义是：

“应用启动完后再执行一段初始化逻辑”

---

## 9. 认证模块 auth：项目怎么登录，怎么知道你是谁

### 9.1 AuthController：对外认证接口

[backend/src/main/java/com/valorantassistant/auth/controller/AuthController.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/controller/AuthController.java)

它暴露了三个接口：

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/me`
- `GET /api/v1/auth/session-context`

### 9.2 LoginRequest：登录入参

[backend/src/main/java/com/valorantassistant/auth/dto/LoginRequest.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/dto/LoginRequest.java)

字段：

- `usernameOrEmail`
- `password`

这里用了 `@NotBlank`，表示这两个字段不能为空。

### 9.3 AuthService：核心认证逻辑

[backend/src/main/java/com/valorantassistant/auth/service/AuthService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/service/AuthService.java)

这个类是认证模块的大脑。

#### login(...)

做的事情：

1. 用用户名或邮箱查用户
2. 如果没查到，返回 401
3. 检查用户状态是不是 `ACTIVE`
4. 用 `PasswordEncoder` 校验密码
5. 更新最后登录时间和 IP
6. 生成 JWT Token
7. 返回 `LoginResponse`

#### getCurrentUser(...)

做的事情：

1. 根据当前认证用户名查 `user`
2. 查 `user_profile`
3. 组装 `CurrentUserResponse`

#### getSessionContext(...)

这是一个比 `/me` 更完整的接口。

它会一次性返回：

- 当前用户
- 用户展示名
- 用户头像
- 当前主玩家信息
- 仪表盘资源地址

这个接口很适合前端页面初始化时一次拉全。

### 9.4 CustomUserDetailsService：给 Spring Security 提供用户信息

[backend/src/main/java/com/valorantassistant/auth/service/CustomUserDetailsService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/service/CustomUserDetailsService.java)

Spring Security 并不直接认识你的 `User` 实体，它需要一种统一格式的“安全用户对象”。

这个类就是把数据库里的用户转换成 Spring Security 能理解的 `UserDetails`。

它做的事情：

1. 根据用户名查数据库
2. 检查用户状态
3. 转成 Spring Security 的用户对象
4. 把角色拼成 `ROLE_ADMIN` 这种格式

### 9.5 DTO：为什么不用直接返回数据库实体

认证模块里有很多 `record`：

- `LoginResponse`
- `CurrentUserResponse`
- `CurrentPlayerResponse`
- `DashboardAssetsResponse`
- `SessionContextResponse`

原因是：

- 数据库实体是“数据库长什么样”
- DTO 是“接口返回给前端什么样”

这两个概念最好分开，不然代码会变得混乱。

---

## 10. 安全模块：JWT 和 Spring Security 在这里怎么配合

这是整个项目最值得反复读的部分。

### 10.1 SecurityConfig：安全总开关

[backend/src/main/java/com/valorantassistant/common/config/SecurityConfig.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/SecurityConfig.java)

这个类定义了整个项目的安全规则。

它做了这些事：

#### 关闭 CSRF

```java
csrf(AbstractHttpConfigurer::disable)
```

因为这个项目主要是前后端接口 + Token 鉴权，不是传统服务端表单站点。

#### 开启 CORS

允许跨域调用。

当前配置比较宽松：

- 允许所有来源 `*`
- 允许常见 HTTP 方法
- 允许所有请求头

#### 使用无状态会话

```java
sessionCreationPolicy(SessionCreationPolicy.STATELESS)
```

意思是服务器不保存传统 Session。

为什么？

因为登录状态放在 JWT 里，客户端自己带过来。

#### 定义哪些接口免登录

当前免登录的有：

- `/valorant-dashboard`
- `/valorant-dashboard/**`
- `/api/v1/auth/login`
- `/actuator/health`
- `/actuator/info`

其他接口默认都要登录。

#### 注册 JWT 过滤器

```java
addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
```

意思是：

在 Spring 默认的用户名密码过滤器前，先执行我们自己的 JWT 认证逻辑。

#### 定义未登录时的统一返回

如果你访问了受保护接口但没带合法 Token，就会返回：

- HTTP 401
- JSON 格式错误体

### 10.2 JwtTokenProvider：生成和校验 Token

[backend/src/main/java/com/valorantassistant/common/config/JwtTokenProvider.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/JwtTokenProvider.java)

这个类的职责很纯粹：

- 生成 JWT
- 解析 JWT
- 校验 JWT

#### generateToken(...)

会把这些信息写进 Token：

- 用户名 `subject`
- 用户 ID `uid`
- 角色 `role`
- 过期时间

#### extractUsername(...)

从 Token 里取出用户名。

#### validateToken(...)

尝试解析 Token，如果失败就认为非法或过期。

### 10.3 JwtAuthenticationFilter：每次请求都先过它

[backend/src/main/java/com/valorantassistant/common/config/JwtAuthenticationFilter.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/JwtAuthenticationFilter.java)

它的逻辑可以背下来：

1. 读取请求头 `Authorization`
2. 看是不是 `Bearer xxx`
3. 用 `JwtTokenProvider` 校验 Token
4. 从 Token 提取用户名
5. 用 `CustomUserDetailsService` 加载用户
6. 创建认证对象
7. 放进 `SecurityContextHolder`

一旦放进去，后面的 Controller 就能通过：

```java
Authentication authentication
```

拿到当前登录用户。

### 10.4 这个项目里的 Authentication 是怎么来的

你会看到很多接口方法里都有：

```java
Authentication authentication
```

这不是你手动传进去的，而是 Spring Security 在请求通过认证后自动提供的。

例如：

```java
authentication.getName()
```

拿到的就是当前用户名。

---

## 11. 用户模块 user：当前登录用户本人的资料

### 11.1 User 实体

[backend/src/main/java/com/valorantassistant/user/domain/User.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/user/domain/User.java)

对应数据库表 `user`。

核心字段：

- `id`
- `username`
- `email`
- `phone`
- `passwordHash`
- `roleCode`
- `status`
- `lastLoginAt`
- `lastLoginIp`
- `deletedAt`

你可以把它理解成“登录账号本体”。

### 11.2 UserProfile 实体

[backend/src/main/java/com/valorantassistant/user/domain/UserProfile.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/user/domain/UserProfile.java)

对应数据库表 `user_profile`。

核心字段：

- `userId`
- `displayName`
- `avatarUrl`
- `regionCode`
- `timezone`
- `preferredLanguage`
- `bio`

它更像是“扩展资料表”。

常见设计思路是：

- `user` 放登录和权限相关字段
- `user_profile` 放展示和个性化字段

### 11.3 UserMapper 和 UserProfileMapper

这两个 `Mapper` 负责访问用户相关表。

`UserMapper` 当前提供了：

- 按用户名查用户
- 按用户名或邮箱查用户
- 判断用户名是否存在

`UserProfileMapper` 当前提供了：

- 按 `userId` 查资料

### 11.4 UserProfileController

[backend/src/main/java/com/valorantassistant/user/controller/UserProfileController.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/user/controller/UserProfileController.java)

当前只开放了一个接口：

- `POST /api/v1/users/me/avatar`

### 11.5 UserProfileService：上传当前用户头像

[backend/src/main/java/com/valorantassistant/user/service/UserProfileService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/user/service/UserProfileService.java)

流程：

1. 根据当前用户名查用户
2. 调用 `ImageStorageService` 上传图片到 OSS
3. 查 `user_profile`
4. 如果没有资料，就创建一个新的 `UserProfile`
5. 把 `avatarUrl` 更新成 OSS 地址
6. 插入或更新数据库
7. 返回 `UserAvatarUploadResponse`

这个方法体现了很典型的 Service 思路：

- 先做身份确认
- 再调基础服务
- 再更新业务数据

---

## 12. 玩家模块 player：一个用户可以绑定自己的游戏玩家资料

### 12.1 Player 实体

[backend/src/main/java/com/valorantassistant/player/domain/Player.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/player/domain/Player.java)

对应数据库表 `player`。

主要字段：

- `id`
- `userId`
- `platform`
- `regionCode`
- `gameName`
- `tagLine`
- `puuid`
- `accountLevel`
- `rankTier`
- `avatarUrl`
- `primary`
- `status`
- `lastSyncedAt`

这个实体表示“某个用户绑定的一个 VALORANT 玩家账号”。

### 12.2 什么是 primary player

项目里有“主玩家”的概念。

字段是：

- `is_primary`

意思是：

一个用户可能有多个玩家账号，但前端仪表盘优先展示主玩家。

### 12.3 PlayerMapper

[backend/src/main/java/com/valorantassistant/player/mapper/PlayerMapper.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/player/mapper/PlayerMapper.java)

当前提供的方法：

- 按 `playerId + userId` 查玩家
- 查某个用户的主玩家
- 查某个用户的第一个玩家

这里很关键的一点是：

所有查询都尽量带上 `userId`，这样才能保证用户只能看到自己的玩家数据。

### 12.4 PlayerProfileController 和 PlayerProfileService

开放接口：

- `POST /api/v1/players/{playerId}/avatar`

处理流程：

1. 根据当前用户名查用户
2. 检查这个 `playerId` 是否属于该用户
3. 上传头像到 OSS
4. 更新 `player.avatar_url`
5. 返回上传结果

这体现了很重要的业务规则：

“你不能改别人的玩家头像”

---

## 13. 比赛模块 match：这是当前核心业务

### 13.1 MatchRecord 实体

[backend/src/main/java/com/valorantassistant/match/domain/MatchRecord.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/domain/MatchRecord.java)

对应表 `match_record`。

它描述“一场比赛本身”。

主要字段：

- `matchCode`
- `platform`
- `regionCode`
- `seasonCode`
- `queueCode`
- `modeCode`
- `mapName`
- `clusterCode`
- `startedAt`
- `endedAt`
- `durationSeconds`
- `winningTeam`
- `redScore`
- `blueScore`
- `roundsPlayed`
- `patchVersion`
- `rawPayload`

### 13.2 PlayerMatchStats 实体

[backend/src/main/java/com/valorantassistant/match/domain/PlayerMatchStats.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/domain/PlayerMatchStats.java)

对应表 `player_match_stats`。

它描述：

“某个玩家在某场比赛里的个人表现”

主要字段：

- `playerId`
- `matchRecordId`
- `teamCode`
- `agentCode`
- `won`
- `kills`
- `deaths`
- `assists`
- `averageCombatScore`
- `damageDealt`
- `damageReceived`
- `headshotRate`
- `firstKills`
- `firstDeaths`
- `plants`
- `defuses`
- `econRating`
- `kastRate`
- `rankTier`

### 13.3 为什么要拆成两张表

这是数据库设计里的典型做法。

#### match_record

放整场比赛共享的数据：

- 地图
- 模式
- 开始时间
- 比分

#### player_match_stats

放某个玩家在这场比赛里的个人数据：

- 击杀
- 死亡
- 助攻
- 爆头率

因为一场比赛会有多个玩家，所以必须拆。

### 13.4 MatchController

[backend/src/main/java/com/valorantassistant/match/controller/MatchController.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/controller/MatchController.java)

开放两个接口：

- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`

这里还用了参数校验：

- `page >= 0`
- `1 <= size <= 100`

### 13.5 MatchQueryService

[backend/src/main/java/com/valorantassistant/match/service/MatchQueryService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/service/MatchQueryService.java)

它负责两类业务：

#### 查询比赛历史

流程：

1. 先检查 `playerId` 是否属于当前用户
2. 创建分页对象 `Page.of(page + 1L, size)`
3. 调用 Mapper 查询比赛历史
4. 把数据库行对象转换为 `MatchHistoryItemResponse`
5. 返回 `MatchHistoryResponse`

注意这里有一个细节：

前端页码用 `0` 开始，但 MyBatis-Plus 分页习惯从 `1` 开始，所以代码里做了 `page + 1L`。

#### 查询比赛详情

流程：

1. 先检查 `playerId` 是否属于当前用户
2. 按 `playerId + matchId` 查询比赛详情
3. 如果查不到，返回 404
4. 组装 `MatchDetailResponse`

### 13.6 PlayerMatchStatsMapper：真正写 SQL 的地方

[backend/src/main/java/com/valorantassistant/match/mapper/PlayerMatchStatsMapper.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/mapper/PlayerMatchStatsMapper.java)

这里用了 `@Select` 直接写 SQL。

#### selectHistoryByPlayerId(...)

把：

- `player_match_stats`
- `match_record`

做 `inner join`，查出比赛历史列表。

#### selectMatchDetail(...)

同样是 join 两张表，但会拿更多详细字段。

### 13.7 Query Row 类是什么

你会看到：

- `MatchHistoryRow`
- `MatchDetailRow`

它们不是数据库实体，也不是接口返回 DTO。

你可以把它们理解成：

“接 SQL 查询结果的中间对象”

这样做的好处是职责清晰：

- Entity：表示表
- Row：表示复杂 SQL 查询结果
- DTO：表示接口输出

---

## 14. 媒体模块 media：图片上传到 OSS 是怎么做的

### 14.1 MediaController

[backend/src/main/java/com/valorantassistant/media/controller/MediaController.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/media/controller/MediaController.java)

开放接口：

- `POST /api/v1/media/images`

参数：

- `file`
- `folder` 可选

### 14.2 MultipartFile 是什么

在 Spring 里，上传文件通常会接成 `MultipartFile`。

你可以把它理解成“后端收到的上传文件对象”。

它能让你读到：

- 文件名
- 文件大小
- Content-Type
- 文件流

### 14.3 ImageStorageService：上传逻辑核心

[backend/src/main/java/com/valorantassistant/media/service/ImageStorageService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/media/service/ImageStorageService.java)

这是当前最完整的基础服务之一。

它的流程是：

1. 检查 OSS 配置是否完整
2. 检查文件是否为空
3. 检查 Content-Type 是否允许
4. 检查文件大小是否超限
5. 构造对象存储路径 `objectKey`
6. 创建 OSS 客户端
7. 上传文件
8. 生成公网访问地址
9. 返回 `ImageUploadResponse`

### 14.4 它支持哪些图片类型

在 `OssStorageProperties` 里定义了允许的类型：

- `image/jpeg`
- `image/png`
- `image/webp`
- `image/gif`
- `image/avif`

### 14.5 objectKey 是什么

`objectKey` 就是文件在对象存储里的路径名。

这个项目会把路径拼成大概这样：

```text
uploads/images/users/1/avatar/2026/06/30/随机文件名.jpg
```

它的组成通常包括：

- 根目录
- 业务目录
- 日期目录
- 随机文件名

这样做的好处：

- 目录更整齐
- 避免重名
- 方便管理

### 14.6 buildPublicUrl(...) 做了什么

上传成功后，前端还需要一个能访问图片的 URL。

这个方法会优先使用：

- `publicBaseUrl`

如果没有，再根据：

- `endpoint`
- `bucket`

拼出默认公网地址。

### 14.7 为什么它要自己校验 folder

`normalizePath(...)` 会检查：

- 不能是空路径垃圾值
- 不能包含 `..`
- 不能包含不允许字符

这是安全意识的一部分，避免用户上传时传入奇怪目录。

---

## 15. 数据库：这个项目的数据关系怎么理解

数据库初始化脚本在 [docs/database/schema.sql](/D:/valorant_matches_assistant/docs/database/schema.sql)。

你可以先只盯住当前真正用到的五张表。

### 15.1 user

作用：

- 存账号
- 存密码哈希
- 存角色
- 存登录状态

### 15.2 user_profile

作用：

- 存用户展示资料

关系：

- `user_profile.user_id -> user.id`
- 基本是一对一

### 15.3 player

作用：

- 存某个用户绑定的游戏账号

关系：

- `player.user_id -> user.id`
- 一个用户可以有多个玩家

### 15.4 match_record

作用：

- 存一场比赛本身的信息

### 15.5 player_match_stats

作用：

- 存某个玩家在某场比赛中的表现

关系：

- `player_match_stats.player_id -> player.id`
- `player_match_stats.match_record_id -> match_record.id`

所以可以记成：

`user -> player -> player_match_stats -> match_record`

### 15.6 knowledge_document

这张表目前已经建了，但当前代码里还没有对应的上传、解析、检索完整链路。

它预示项目后面想做知识库功能。

### 15.7 chat_session 和 chat_message

这两张表也已经建了，但当前没有相关 Service 和 Controller 对外开放。

它们说明项目未来想做聊天或 AI 对话能力。

---

## 16. 配置文件怎么读

### 16.1 application.yml

[backend/src/main/resources/application.yml](/D:/valorant_matches_assistant/backend/src/main/resources/application.yml)

这是主配置文件，里面配置了：

- 服务端口
- 上下文路径
- 数据库连接
- 文件上传大小限制
- MyBatis-Plus
- Actuator 暴露接口
- JWT 配置
- 仪表盘资源配置
- OSS 配置
- 启动初始化管理员配置

### 16.2 `${环境变量:默认值}` 是什么

例如：

```yml
server:
  port: ${VALORANT_ASSISTANT_API_PORT:8080}
```

意思是：

- 如果环境变量 `VALORANT_ASSISTANT_API_PORT` 存在，就用它
- 否则用默认值 `8080`

### 16.3 application-local.yml

[backend/src/main/resources/application-local.yml](/D:/valorant_matches_assistant/backend/src/main/resources/application-local.yml)

这是本地开发常用覆盖配置。

它目前主要给了：

- 本地 MySQL 连接
- 本地 dashboard 资源配置
- 本地 bootstrap 开关

### 16.4 .env.example

[.env.example](/D:/valorant_matches_assistant/.env.example)

它列出了这项目预期会用到的环境变量。

需要注意：

- 里面有些变量是“未来规划用的”，比如 Redis、MinIO、Milvus
- 当前后端代码真实使用到的，主要是 MySQL、JWT、OSS、上传限制、bootstrap 这些

---

## 17. 注解速查：看这项目代码时你最常遇到的标签

### 17.1 Web 层注解

- `@RestController`：返回 JSON 接口
- `@Controller`：返回页面或做转发
- `@RequestMapping`：定义统一路径前缀
- `@GetMapping`：处理 GET 请求
- `@PostMapping`：处理 POST 请求
- `@PathVariable`：读取 URL 路径参数
- `@RequestParam`：读取查询参数或表单参数
- `@RequestBody`：读取 JSON 请求体

### 17.2 Spring 组件注解

- `@Service`：业务服务
- `@Configuration`：配置类
- `@Bean`：把方法返回对象注册给 Spring 管理
- `@Component`：普通组件

### 17.3 配置相关注解

- `@ConfigurationProperties`：把配置文件映射成 Java 对象
- `@EnableConfigurationProperties`：启用配置对象
- `@Value`：读取单个配置值

### 17.4 校验相关注解

- `@Valid`：触发对象校验
- `@Validated`：启用方法参数校验
- `@NotBlank`：不能为空字符串
- `@Min`：最小值
- `@Max`：最大值

### 17.5 MyBatis-Plus 注解

- `@TableName`：表名
- `@TableId`：主键
- `@TableField`：字段映射

---

## 18. 这个项目里的 Mapper 和 SQL 怎么理解

很多新手第一次看到 `Mapper` 会困惑：为什么不是像 Python 一样直接写 SQL 文件？

这里的方式是：

- Java 接口里定义方法
- 用 `@Select` 直接把 SQL 写在方法上

例如 `UserMapper`：

- `selectByUsername(...)`
- `selectByUsernameOrEmail(...)`

例如 `PlayerMatchStatsMapper`：

- 通过 SQL join 两张表查比赛数据

### 18.1 BaseMapper 是什么

`BaseMapper<T>` 是 MyBatis-Plus 提供的通用 Mapper。

有了它，很多基础方法你不需要自己写：

- `insert`
- `updateById`
- `selectById`

所以当前项目只为“特殊查询”额外写 SQL。

### 18.2 为什么这里既有 BaseMapper，又有 @Select 自定义 SQL

因为现实项目里通常两种都要：

- 简单增删改查用通用能力
- 复杂查询用自定义 SQL

---

## 19. 静态仪表盘页面是怎么和后端配合的

前端静态页面在：

- [backend/src/main/resources/static/valorant-dashboard/index.html](/D:/valorant_matches_assistant/backend/src/main/resources/static/valorant-dashboard/index.html)
- [backend/src/main/resources/static/valorant-dashboard/app.js](/D:/valorant_matches_assistant/backend/src/main/resources/static/valorant-dashboard/app.js)

### 19.1 这是个什么页面

这是一个不依赖 Vue/React 的纯静态页面。

它用：

- `HTML` 负责结构
- `CSS` 负责样式
- `JavaScript` 负责调用后端 API 和更新页面

### 19.2 页面启动时会做什么

`app.js` 初始化时会：

1. 绑定登录、刷新、登出、上传头像事件
2. 看本地存储里有没有 Token
3. 如果有 Token，就请求 `/api/v1/auth/session-context`
4. 再根据主玩家信息请求比赛历史和详情
5. 渲染到页面上

### 19.3 它用了哪些后端接口

主要用了：

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/session-context`
- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`
- `POST /api/v1/users/me/avatar`

### 19.4 为什么它有 previewMatches

`app.js` 里有一组预览数据 `previewMatches`，但当前代码主流程更偏向：

- 未登录时显示锁定态
- 登录后再拉真实数据

你可以把这些预览数据理解成“页面设计阶段保留的前端假数据资产”。

### 19.5 dashboardAssets 的作用

后端 `session-context` 会把资源地址返回给前端，前端据此决定：

- logo 显示什么
- 地图图显示什么
- agent 图显示什么
- 默认头像显示什么

这样前后端就能通过配置灵活切换本地资源和 OSS 资源。

---

## 20. 当前开放的接口清单

### 20.1 无需登录

- `POST /api/v1/auth/login`
- `GET /actuator/health`
- `GET /actuator/info`
- `GET /valorant-dashboard`

### 20.2 需要登录

- `GET /api/v1/auth/me`
- `GET /api/v1/auth/session-context`
- `GET /api/v1/players/{playerId}/matches`
- `GET /api/v1/players/{playerId}/matches/{matchId}`
- `POST /api/v1/media/images`
- `POST /api/v1/users/me/avatar`
- `POST /api/v1/players/{playerId}/avatar`

---

## 21. 你可以怎样从零开始读这个项目

如果你直接从任意文件跳着看，会很容易乱。建议按这个顺序读：

### 第一轮：先建立整体印象

1. [backend/pom.xml](/D:/valorant_matches_assistant/backend/pom.xml)
2. [backend/src/main/resources/application.yml](/D:/valorant_matches_assistant/backend/src/main/resources/application.yml)
3. [backend/src/main/java/com/valorantassistant/ValorantAssistantApplication.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/ValorantAssistantApplication.java)

### 第二轮：理解项目怎么保护接口

1. [backend/src/main/java/com/valorantassistant/common/config/SecurityConfig.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/SecurityConfig.java)
2. [backend/src/main/java/com/valorantassistant/common/config/JwtAuthenticationFilter.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/JwtAuthenticationFilter.java)
3. [backend/src/main/java/com/valorantassistant/common/config/JwtTokenProvider.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/common/config/JwtTokenProvider.java)
4. [backend/src/main/java/com/valorantassistant/auth/service/CustomUserDetailsService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/service/CustomUserDetailsService.java)

### 第三轮：理解登录业务

1. [backend/src/main/java/com/valorantassistant/auth/controller/AuthController.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/controller/AuthController.java)
2. [backend/src/main/java/com/valorantassistant/auth/service/AuthService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/auth/service/AuthService.java)
3. `auth/dto` 目录

### 第四轮：理解比赛查询业务

1. [backend/src/main/java/com/valorantassistant/match/controller/MatchController.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/controller/MatchController.java)
2. [backend/src/main/java/com/valorantassistant/match/service/MatchQueryService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/service/MatchQueryService.java)
3. [backend/src/main/java/com/valorantassistant/match/mapper/PlayerMatchStatsMapper.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/match/mapper/PlayerMatchStatsMapper.java)
4. `match/dto` 和 `match/query` 目录

### 第五轮：理解上传与头像业务

1. [backend/src/main/java/com/valorantassistant/media/service/ImageStorageService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/media/service/ImageStorageService.java)
2. [backend/src/main/java/com/valorantassistant/user/service/UserProfileService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/user/service/UserProfileService.java)
3. [backend/src/main/java/com/valorantassistant/player/service/PlayerProfileService.java](/D:/valorant_matches_assistant/backend/src/main/java/com/valorantassistant/player/service/PlayerProfileService.java)

### 第六轮：对照数据库理解实体

1. [docs/database/schema.sql](/D:/valorant_matches_assistant/docs/database/schema.sql)
2. `user/domain`
3. `player/domain`
4. `match/domain`

### 第七轮：最后再看前端静态页

1. [backend/src/main/resources/static/valorant-dashboard/index.html](/D:/valorant_matches_assistant/backend/src/main/resources/static/valorant-dashboard/index.html)
2. [backend/src/main/resources/static/valorant-dashboard/app.js](/D:/valorant_matches_assistant/backend/src/main/resources/static/valorant-dashboard/app.js)

---

## 22. 本地运行这个项目，最小需要知道什么

### 22.1 你至少需要这些环境

- `JDK 17`
- `Maven`
- `MySQL`

### 22.2 数据库准备

先执行：

- [docs/database/schema.sql](/D:/valorant_matches_assistant/docs/database/schema.sql)

### 22.3 配置数据库

最简单的方式是使用 `application-local.yml` 的默认本地配置，或者按 [.env.example](/D:/valorant_matches_assistant/.env.example) 设置环境变量。

### 22.4 启动命令

在 `backend` 目录执行：

```bash
mvn spring-boot:run
```

### 22.5 启动后能访问什么

- 后端首页上下文通常是 `http://localhost:8080`
- 仪表盘页面：`http://localhost:8080/valorant-dashboard`
- 健康检查：`http://localhost:8080/actuator/health`

### 22.6 默认管理员账号

如果 bootstrap 开关开启，且数据库里还没有管理员，启动时会自动创建：

- 用户名：`admin`
- 密码：`Admin@123456`

这些默认值来自配置，可自行修改。

---

## 23. 这个项目有哪些值得你特别记住的“设计意识”

即使你还不熟 Java，这些工程习惯也值得学习。

### 23.1 分层清晰

- Controller 不直接写复杂业务
- Service 不直接暴露给前端
- Mapper 专心查数据库

### 23.2 认证和业务分开

- Security 负责“你是不是谁”
- Service 负责“你能做什么”

### 23.3 统一返回和统一异常

这让前端联调体验更稳定。

### 23.4 DTO 和实体分离

数据库结构不等于接口结构。

### 23.5 未来能力提前留表，但不假装已完成

这个项目已经有知识库和聊天相关表，但业务接口尚未完成。  
读代码时一定要分清“预留”和“落地”。

---

## 24. 你现在应该已经能回答这些问题

如果你能回答下面这些问题，说明你已经真正入门这个项目了。

1. `Spring Boot` 在这个项目里主要负责什么？
2. `SecurityConfig` 为什么重要？
3. JWT 是怎么从请求头进入 `Authentication` 的？
4. `Controller`、`Service`、`Mapper` 各自负责什么？
5. `user` 和 `user_profile` 为什么分开？
6. `match_record` 和 `player_match_stats` 为什么分开？
7. 为什么查询玩家比赛前要先校验 `playerId` 属于当前用户？
8. 用户头像上传成功后，为什么还要更新数据库？
9. `session-context` 为什么比 `me` 更适合前端初始化？
10. 哪些模块只是未来预留，还没有完整实现？

如果这些问题你还答不顺，不是你学不会，只是还需要再读一轮第 7 到第 15 节。

---

## 25. 最后的学习建议

对于这个项目，你完全没必要先把 Java 全学完再回来读代码。更有效的顺序是：

1. 先理解这份文档的整体脑图。
2. 再按第 21 节的顺序读源码。
3. 每读一个接口，就用一句话复述“请求从哪来，经过谁，查什么，返回什么”。
4. 最后自己手动画出一张图：`浏览器 -> Security -> Controller -> Service -> Mapper -> MySQL`

只要这条主链路打通，你再学更深的 Java 语法、Spring 注解、数据库设计，速度会快很多。
