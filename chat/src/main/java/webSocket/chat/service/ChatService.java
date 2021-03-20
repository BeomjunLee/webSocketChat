package webSocket.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webSocket.chat.domain.Room;
import webSocket.chat.repository.ChatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;

    public List<Room> findAllRoom() {
        return chatRepository.findAll();

    }
    public Room findById(Long id) {
        return chatRepository.findById(id).orElseThrow();
    }

    public Room createRoom(String name) {
        return chatRepository.save(Room.createRoom(name));
    }

}
