## 실시간 채팅 서비스

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
