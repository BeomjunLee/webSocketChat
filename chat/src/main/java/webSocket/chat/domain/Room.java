package webSocket.chat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import webSocket.chat.dto.ChatMessage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;
    private String name;

    @Builder
    public Room(String name) {
        this.name = name;
    }

    public static Room createRoom(String name) {
        return Room.builder().name(name).build();
    }

}