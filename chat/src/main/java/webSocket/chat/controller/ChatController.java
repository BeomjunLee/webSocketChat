package webSocket.chat.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import webSocket.chat.dto.ChatMessage;
import webSocket.chat.service.ChatService;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if(ChatMessage.MessageType.JOIN.equals(message.getMessageType()))
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
        messageTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId());
    }





}
