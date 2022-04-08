package com.mostafamohamed.Chat.Chat;

public class Messages {
    String messageId,
            senderId,
    message;

    public Messages(String messageId, String senderId, String message) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.message = message;
    }

    public Messages() {
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}
