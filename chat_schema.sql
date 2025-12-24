-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS ai_chat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE ai_chat;

-- 创建会话表（用于存储类型和会话ID）
CREATE TABLE IF NOT EXISTS chat_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    chat_id VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 创建会话历史表（用于存储会话历史记录）
CREATE TABLE IF NOT EXISTS chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chat_id) REFERENCES chat_session(chat_id) ON DELETE CASCADE
);

-- 创建索引以提高查询性能
CREATE INDEX idx_chat_session_type ON chat_session(type);
CREATE INDEX idx_chat_message_chat_id ON chat_message(chat_id);
CREATE INDEX idx_chat_message_timestamp ON chat_message(timestamp);