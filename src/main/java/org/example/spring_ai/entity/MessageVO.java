package org.example.spring_ai.entity;

import org.springframework.ai.chat.messages.Message;
import org.springframework.aot.hint.TypeHintExtensionsKt;

public class MessageVO {
    private String role;
    private String content;
    public MessageVO(String role, String content) {
        this.role = role;
        this.content = content;
    }
    public MessageVO(Message message) {
        switch (message.getMessageType()){
            case USER:
                this.role = "user";
                break;
            case ASSISTANT:
                this.role = "assistant";
                break;
            default:
                this.role = "";
        }
        content = message.getText();
    }
    public MessageVO() {}
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
