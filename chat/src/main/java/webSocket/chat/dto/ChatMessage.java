package webSocket.chat.dto;

import lombok.Data;

@Data
public class ChatMessage {
    public enum MessageType{
        JOIN, CHAT, OUT
    }

    private MessageType messageType;
    private Long roomId;
    private String sender;
    private String message;
}
