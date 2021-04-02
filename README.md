## 실시간 채팅 서비스

### STOMP 흐름 
<img src="https://user-images.githubusercontent.com/69130921/111916872-67842700-8ac0-11eb-9ea9-d9a7a84346af.png"><br>
1. 클라이언트(Sender)가 메세지를 보내면 STOMP 통신으로 서버에 메세지가 전달된다.

2. Controller의 @MessageMapping에 의해 메세지를 받는다.

3. Controller의 @SendTo로 특정 topic을(/1) 구독(/room) 하는 클라이언트에게 메세지를 보낸다.<br>
 (구독은 /room 으로 보면되고 특정 topic은 채팅방 id인 /1로 보면된다. -> /room/1)<br><br>
 
### 내 블로그에서 상세 설명 보기(포스팅)
https://blog.naver.com/qjawnswkd/222283176175<br><br>

### 1. Gradle 의존성 설정
```java
	implementation 'org.springframework.boot:spring-boot-starter-websocket'
	implementation 'org.webjars:webjars-locator-core'
	implementation 'org.webjars:sockjs-client:1.0.2'
	implementation 'org.webjars:stomp-websocket:2.3.3'
	implementation 'org.webjars:bootstrap:3.3.7'
	implementation 'org.webjars:jquery:3.1.1-1'
```

### 2. WebSocketConfig 설정
```java
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/send");       //클라이언트에서 보낸 메세지를 받을 prefix
        registry.enableSimpleBroker("/room");    //해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")   //SockJS 연결 주소
                .withSockJS(); //버전 낮은 브라우저에서도 적용 가능
        // 주소 : ws://localhost:8080/ws-stomp
    }
}
```

### 3. 컨트롤러
```java
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/room/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ChatMessage test(@DestinationVariable Long roomId, ChatMessage message) {

        //채팅 저장
        Chat chat = chatService.createChat(roomId, message.getSender(), message.getMessage());
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(chat.getSender())
                .message(chat.getMessage())
                .build();
    }
}
```

### 4.클라이언트
```javascript
<script th:inline="javascript">
    var stompClient = null;
    var roomId = [[${roomId}]];
    var chatList = [[${chatList}]];

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#chatting").html("");
    }

    function connect() {
        var socket = new SockJS('/ws-stomp');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            loadChat(chatList)  //저장된 채팅 불러오기

            //구독
            stompClient.subscribe('/room/'+roomId, function (chatMessage) {
                showChat(JSON.parse(chatMessage.body));
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    //html 에서 입력값, roomId 를 받아서 Controller 로 전달
    function sendChat() {
        stompClient.send("/send/"+roomId, {},
            JSON.stringify({
                'sender': $("#sender").val(),
                'message' : $("#message").val()
            }));
    }

    //저장된 채팅 불러오기
    function loadChat(chatList){
        if(chatList != null) {
            for(chat in chatList) {
                $("#chatting").append(
                    "<tr><td>" + "[" + chatList[chat].sender + "]" + chatList[chat].message + "</td></tr>"
                );
            }
        }
    }

    //보낸 채팅 보기
    function showChat(chatMessage) {
        $("#chatting").append(
            "<tr><td>" + "[" + chatMessage.sender + "]" + chatMessage.message + "</td></tr>"
        );
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $( "#connect" ).click(function() { connect(); });
        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendChat(); });
    });
</script>
<script>
    //창 키면 바로 연결
    window.onload = function (){
        connect();
    }

    window.BeforeUnloadEvent = function (){
        disconnect();
    }
</script>
```

### Redis 추가중
