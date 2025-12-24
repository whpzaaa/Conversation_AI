# Spring AI 聊天项目

这是一个基于 Spring Boot 和 Spring AI 构建的聊天应用，使用 Ollama 作为 AI 模型后端，MySQL 作为持久化存储。

## 技术栈

- **框架**: Spring Boot 3.5.9
- **AI 集成**: Spring AI 1.1.2
- **AI 模型**: Ollama (deepseek-r1:8b)
- **数据库**: MySQL 8.x
- **持久层**: Spring Data JPA
- **构建工具**: Maven
- **编程语言**: Java 17

## 功能特性

- AI 聊天对话功能
- 会话历史记录持久化存储
- 按类型分组管理会话
- REST API 接口
- 数据持久化（系统重启后数据不丢失）

## 数据库设计

### 表结构

1. **会话表 (chat_session)**
   ```sql
   CREATE TABLE chat_session (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       type VARCHAR(255) NOT NULL,
       chat_id VARCHAR(255) NOT NULL UNIQUE,
       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
   );
   ```

2. **会话历史表 (chat_message)**
   ```sql
   CREATE TABLE chat_message (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       chat_id VARCHAR(255) NOT NULL,
       role VARCHAR(50) NOT NULL,
       content TEXT NOT NULL,
       timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (chat_id) REFERENCES chat_session(chat_id) ON DELETE CASCADE
   );
   ```

### 数据库脚本

数据库创建脚本已在 `chat_schema.sql` 文件中提供，包含完整的表结构和索引定义。

## 快速开始

### 1. 环境要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本
- MySQL 8.x
- Ollama (已安装 deepseek-r1:8b 模型)

### 2. 配置数据库

1. 创建数据库：
   ```sql
   CREATE DATABASE ai_chat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. 修改 `src/main/resources/application.yaml` 中的数据库配置：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/ai_chat?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
       username: your_username
       password: your_password
   ```

### 3. 配置 Ollama

确保 Ollama 服务已启动，并已下载 deepseek-r1:8b 模型：

```bash
# 启动 Ollama 服务
ollama serve

# 下载模型
ollama pull deepseek-r1:8b
```

### 4. 运行项目

使用 Maven 命令运行项目：

```bash
mvn spring-boot:run
```

## API 文档

### 获取会话 ID 列表

```
GET /ai/history/{type}
```

**参数**:
- `type`: 会话类型

**返回示例**:
```json
[
  "session-1",
  "session-2",
  "session-3"
]
```

### 获取会话历史

```
GET /ai/history/{type}/{chatId}
```

**参数**:
- `type`: 会话类型
- `chatId`: 会话 ID

**返回示例**:
```json
[
  {
    "role": "user",
    "content": "你好，我是用户"
  },
  {
    "role": "assistant",
    "content": "你好，我是 AI 助手"
  }
]
```

## 项目结构

```
src/main/java/org/example/spring_ai/
├── controller/           # 控制器层
│   └── ChatHistoryController.java
├── entity/              # 实体类
│   ├── ChatSession.java
│   ├── ChatMessage.java
│   └── MessageVO.java
├── memory/              # 会话内存实现
│   └── DbChatMemory.java
├── repository/          # 数据访问层
│   ├── ChatHistoryRepository.java
│   ├── DbChatHistoryRepository.java
│   ├── ChatSessionRepository.java
│   └── ChatMessageRepository.java
├── config/              # 配置类
│   ├── CommonConfig.java
│   └── CorsConfig.java
└── SpringAiApplication.java  # 主应用类
```

## 开发说明

### 会话历史管理

项目使用自定义的 `DbChatMemory` 类替代了 Spring AI 自带的 `MessageWindowChatMemory`，实现了会话历史的数据库持久化。

### 扩展功能

1. **添加新的会话类型**：
   - 无需修改代码，直接在 API 中使用新的类型参数即可

2. **修改 AI 模型**：
   - 在 `application.yaml` 中修改 `spring.ai.ollama.chat.model` 配置

3. **调整 AI 系统提示词**：
   - 在 `CommonConfig.java` 中修改 `defaultSystem` 配置

## 注意事项

1. 确保 Ollama 服务已正确启动，并且 `deepseek-r1:8b` 模型已下载
2. 数据库连接配置必须正确，否则项目无法启动
3. 项目使用 Spring Data JPA 的 `ddl-auto: update` 配置，会自动创建和更新数据库表结构
4. 系统重启后，会话历史会从数据库中恢复

## 许可证

MIT License
