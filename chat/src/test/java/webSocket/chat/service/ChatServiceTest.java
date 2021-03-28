package webSocket.chat.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webSocket.chat.domain.Chat;
import webSocket.chat.domain.Room;
import webSocket.chat.repository.ChatRepository;
import webSocket.chat.repository.RoomRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChatServiceTest {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    RoomRepository roomRepository;

    @Test
    void redisTest() {
        Room room = Room.createRoom("room1");
        roomRepository.save(room);
        Chat chat = Chat.createChat(room, "범준", "안녕하세요");
        chatRepository.save(chat);

        Chat findChat = chatRepository.findById(chat.getId()).get();

        System.out.println("findChat = " + findChat);
        System.out.println("findChat.getRoom() = " + findChat.getRoom());

        List<Chat> rooms = chatRepository.findAllByRoomId(room.getId());
        System.out.println("rooms = " + rooms);
    }

}