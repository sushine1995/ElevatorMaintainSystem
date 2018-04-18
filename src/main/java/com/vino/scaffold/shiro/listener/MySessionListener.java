package com.vino.scaffold.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

public class MySessionListener extends SessionListenerAdapter{

	@Override
	public void onExpiration(Session session) {
		// TODO Auto-generated method stub
		super.onExpiration(session);
		System.out.println("id:"+session.getId()+" host:"+session.getHost()+" 过期");
		
	}

	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub
		super.onStart(session);
		System.out.println("id:"+session.getId()+" host:"+session.getHost()+" 开始");
	}

	@Override
	public void onStop(Session session) {
		// TODO Auto-generated method stub
		super.onStop(session);
		System.out.println("id:"+session.getId()+" host:"+session.getHost()+" 停止");
	}

	
}
