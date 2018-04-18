package com.vino.authentication.entity;

public class TokenModel {
	 //用户id
    private long userId;
 
    //jwt生成的token字符串
    private String token;

	public TokenModel(long userId, String token) {
		this.userId=userId;
		this.token=token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}
