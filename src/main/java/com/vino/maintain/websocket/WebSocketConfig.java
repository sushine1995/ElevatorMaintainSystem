package com.vino.maintain.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		///设置STOMP终端地址
		registry.addEndpoint("/ws").withSockJS();		
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/notice","/topic");//开启一个simpleBroker/notice
		//这里定义一个broker,并且确定前缀，可以使用@sendto来转发到指定/broker-prefix/xxx broker channel,然后根据这个路径来转发到相应的broker（订阅了该路径的客户端，就会收到消息），默认则是/broker-prefix/methodName
		config.setApplicationDestinationPrefixes("/app");//设置应用前缀,这个前缀不必与web应用名相同

	}
	
}
