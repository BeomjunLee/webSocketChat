package webSocket.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webSocket.chat.domain.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByRoomId(Long roomId);
}
