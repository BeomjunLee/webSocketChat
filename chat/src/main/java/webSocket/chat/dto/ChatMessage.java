package webSocket.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {

    private MsgType type;
    private String content;
    private String sender;
}
