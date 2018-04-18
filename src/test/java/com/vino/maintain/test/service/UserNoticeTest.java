package com.vino.maintain.test.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vino.maintain.entity.notice.UserNotice;
import com.vino.maintain.service.notice.UserNoticeService;
import com.vino.scaffold.shiro.service.UserService;

public class UserNoticeTest {
	private UserNoticeService userNoticeService;
	private  ClassPathXmlApplicationContext ctx ;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Before
	public void setUp() throws Exception {
		 System.out.println("进行准备工作");
		 ctx =  new ClassPathXmlApplicationContext("applicationContext.xml"); 
		 userNoticeService=ctx.getBean("userNoticeService",UserNoticeService.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		for(int i=0;i<10;i++){
			UserNotice notice=new UserNotice();
			notice.setContent("这是第"+i+"则通知");
			notice.setCreateTime(new Date());
			notice.setCreatorName("admin");
			notice.setType("orderExpire");
			userNoticeService.save(notice);
		}
			
	}

}
