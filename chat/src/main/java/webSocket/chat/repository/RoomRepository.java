package webSocket.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webSocket.chat.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
