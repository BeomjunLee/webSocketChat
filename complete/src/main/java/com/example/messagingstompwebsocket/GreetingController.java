package com.example.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


	@MessageMapping("/hello")
	@SendTo("/topic/greetings") // topic의 greetings 라는 방
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(500); // simulated delay
		return new Greeting("Hello, " + message.getName() + "!");
	}

}
