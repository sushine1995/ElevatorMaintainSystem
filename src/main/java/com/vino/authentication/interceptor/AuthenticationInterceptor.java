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
 * �����֤�������������ƶ���api ��������ǰ�ж��û��Ƿ����
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
		// �������ӳ�䵽����ֱ��ͨ��
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		if (method.getAnnotation(Authentication.class) != null) {// ��Authenticationע��ķ�������Ҫ����token��֤
			String token = getTokenFromHttp(request);
			// ��֤token
			TokenModel model = manager.getToken(token);// ��token�н�����userid,�п��ܷ��ص���null

			if (manager.checkToken(model)) {
				// ���token��֤�ɹ�����token��Ӧ���û�id����request�У�����֮��ע��
			//	request.setAttribute(AuthenticationConstants.CURRENT_USER_ID, model.getUserId());
				return true;
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}
		}
		// û��ע�⣬ͨ����������
		return true;

	}

	private String getTokenFromHttp(HttpServletRequest request) {
		// ��header�еõ�token
		String token = request.getHeader(AuthenticationConstants.AUTHENTICATION);
		if (token == null || "".equals(token)) {// ��header�в�����token������query��body�в���

			token = request.getParameter(AuthenticationConstants.AUTHENTICATION);

		}
		return token;
	}
}
