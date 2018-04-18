package com.vino.authentication.constant;

import java.security.Key;

import io.jsonwebtoken.impl.crypto.MacProvider;

public class AuthenticationConstants {
	 /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * token有效期（天）
     */
    public static final int TOKEN_EXPIRES_DAY = 30;

    /**
     * 存放Authorization的header、body、query字段名
     */
    public static final String AUTHENTICATION = "token";
    /**
     * 用于加密token的key
     */
    public static final Key KEY=MacProvider.generateKey();
}
