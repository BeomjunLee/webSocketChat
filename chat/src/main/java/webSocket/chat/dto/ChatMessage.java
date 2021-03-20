package webSocket.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public enum MessageType{
        JOIN, CHAT, OUT
    }

    private MessageType messageType;
    private Long roomId;
    private String name;
    private String message;

    public ChatMessage(String name) {
        this.name = name;
    }
}
