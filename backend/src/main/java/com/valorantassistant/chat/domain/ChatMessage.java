package com.valorantassistant.chat.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.valorantassistant.knowledge.domain.KnowledgeDocument;
import com.valorantassistant.match.domain.MatchRecord;
import java.time.LocalDateTime;

@TableName("chat_message")
public class ChatMessage {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("chat_session_id")
    private Long chatSessionId;

    @TableField("sender_type")
    private String senderType;

    @TableField("message_type")
    private String messageType;

    @TableField("content_text")
    private String contentText;

    @TableField("token_count")
    private Integer tokenCount;

    @TableField("knowledge_document_id")
    private Long knowledgeDocumentId;

    @TableField("cited_match_record_id")
    private Long citedMatchRecordId;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField(exist = false)
    private ChatSession chatSession;

    @TableField(exist = false)
    private KnowledgeDocument knowledgeDocument;

    @TableField(exist = false)
    private MatchRecord citedMatchRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatSessionId() {
        return chatSessionId;
    }

    public void setChatSessionId(Long chatSessionId) {
        this.chatSessionId = chatSessionId;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public Integer getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(Integer tokenCount) {
        this.tokenCount = tokenCount;
    }

    public Long getKnowledgeDocumentId() {
        return knowledgeDocumentId;
    }

    public void setKnowledgeDocumentId(Long knowledgeDocumentId) {
        this.knowledgeDocumentId = knowledgeDocumentId;
    }

    public Long getCitedMatchRecordId() {
        return citedMatchRecordId;
    }

    public void setCitedMatchRecordId(Long citedMatchRecordId) {
        this.citedMatchRecordId = citedMatchRecordId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ChatSession getChatSession() {
        return chatSession;
    }

    public void setChatSession(ChatSession chatSession) {
        this.chatSession = chatSession;
    }

    public KnowledgeDocument getKnowledgeDocument() {
        return knowledgeDocument;
    }

    public void setKnowledgeDocument(KnowledgeDocument knowledgeDocument) {
        this.knowledgeDocument = knowledgeDocument;
    }

    public MatchRecord getCitedMatchRecord() {
        return citedMatchRecord;
    }

    public void setCitedMatchRecord(MatchRecord citedMatchRecord) {
        this.citedMatchRecord = citedMatchRecord;
    }
}
