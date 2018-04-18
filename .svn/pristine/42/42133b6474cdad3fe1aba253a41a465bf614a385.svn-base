package com.vino.maintain.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.vino.maintain.service.notice.UserNoticeService;
import com.vino.maintain.service.notice.UserNoticeServiceImpl;
@Controller
public class GreetController {
	@Autowired
	private UserNoticeService userNoticeService;
 	@MessageMapping("/hello")
    @SendTo("/topic/greetings")//发送给订阅/topic/greetings的用户
 	//The return value is broadcast to all subscribers to "/topic/greetings" as specified in the @SendTo annotation.
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }
 	@MessageMapping("/connect")//默认处理完会转发给/topic/connect broker
 	public String sendMessage(){
 		
 		return "from server";
 	}
 	
	@MessageMapping("/connect2")
 	public void connect(){
		//userNoticeService.faultOrderFinished();
 		
 	}
	@SubscribeMapping("/topic/connect")//当客户端订阅的时候触发该方法，不会转发给broker，直接发给客户端
	//一般用于初始化
 	public String connect3(){
		System.out.println("订阅/topic/connect");
		return "sub success";
 		
 	}

}
