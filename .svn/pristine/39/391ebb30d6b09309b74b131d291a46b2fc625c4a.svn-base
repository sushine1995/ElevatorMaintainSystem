package com.vino.maintain.websocket;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vino.maintain.entity.notice.UserNotice;
import com.vino.maintain.service.notice.UserNoticeService;
import com.vino.scaffold.controller.base.BaseController;

@Controller
public class MessageNoticeController{
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private UserNoticeService userNoticeService;
	@SubscribeMapping("/notice/count")//订阅的时候执行，一般用于初始化
	public Map<String,Object> getNoReadNoticeCount(){
		
		List<UserNotice> notices=userNoticeService.findNoReadNotices();
		Map<String,Object> result=new HashMap<>();
		result.put("noReadCount",notices.size());
		result.put("noReadNotices", notices);
		return result;
	}
	@MessageMapping("/notice/content")
	@SendTo("/topic/count")
	public String getContent(){
		System.out.println("获取内容");
		return "通知内容";
	}
	
	
}
