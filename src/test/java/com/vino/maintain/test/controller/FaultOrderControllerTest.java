package com.vino.maintain.test.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:spring-mvc.xml" })
public class FaultOrderControllerTest {
	@Autowired
	private WebApplicationContext wac;
	EntityManagerFactory emf;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		// webAppContextSetup 注意上面的static import
		// webAppContextSetup 构造的WEB容器可以添加fileter 但是不能添加listenCLASS
		// WebApplicationContext context =
		// ContextLoader.getCurrentWebApplicationContext();
		// 如果控制器包含如上方法 则会报空指针
		this.mockMvc = webAppContextSetup(this.wac).build();
		//用来解决懒加载no session的问题 
		emf = (EntityManagerFactory) wac.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		EntityManagerHolder emHolder = new EntityManagerHolder(em);
		TransactionSynchronizationManager.bindResource(emf, emHolder);
	}

	@After
	public void clean() {
		TransactionSynchronizationManager.unbindResource(emf);
	}

	@Test
	// 有些单元测试你不希望回滚
	// @Rollback(false)
	public void detail() throws Exception {
	

		mockMvc.perform(
				get("/api/faultOrder/detail?id=40").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk()).andDo(print());
	
	}

	@Test
	public void listByPage() throws Exception {

		mockMvc.perform((get("/api/faultOrder/list?pageSize=10&pageNumber=1&groupId=3&type=1")))
				.andExpect(status().isOk()).andDo(print());

	}
	@Test
	public void acceptOrder() throws Exception{
		mockMvc.perform(post("/api/faultOrder/accept").param("employeeId", "5").param("id", "40").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isOk()).andDo(print());

	}

}
