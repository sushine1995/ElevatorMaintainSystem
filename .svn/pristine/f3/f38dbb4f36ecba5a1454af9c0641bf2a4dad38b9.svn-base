package com.vino.authentication.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vino.authentication.annotation.Authentication;
import com.vino.authentication.constant.AuthenticationConstants;
import com.vino.authentication.entity.TokenModel;
import com.vino.authentication.manager.TokenManager;

/**
 * 身份认证拦截器，用于移动端api 在请求处理前判断用户是否存在
 * 
 * @author vino007
 *
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private TokenManager manager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		if (method.getAnnotation(Authentication.class) != null) {// 有Authentication注解的方法，需要进行token认证
			String token = getTokenFromHttp(request);
			// 验证token
			TokenModel model = manager.getToken(token);// 从token中解析出userid,有可能返回的是null

			if (manager.checkToken(model)) {
				// 如果token验证成功，将token对应的用户id存在request中，便于之后注入
			//	request.setAttribute(AuthenticationConstants.CURRENT_USER_ID, model.getUserId());
				return true;
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}
		}
		// 没有注解，通过该拦截器
		return true;

	}

	private String getTokenFromHttp(HttpServletRequest request) {
		// 从header中得到token
		String token = request.getHeader(AuthenticationConstants.AUTHENTICATION);
		if (token == null || "".equals(token)) {// 若header中不存在token，则在query或body中查找

			token = request.getParameter(AuthenticationConstants.AUTHENTICATION);

		}
		return token;
	}
}
