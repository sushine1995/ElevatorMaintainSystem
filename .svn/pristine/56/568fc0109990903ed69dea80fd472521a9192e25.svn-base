package com.vino.maintain.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vino.authentication.constant.AuthenticationConstants;
import com.vino.authentication.entity.TokenModel;
import com.vino.authentication.manager.TokenManager;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.service.EmployeeService;
import com.vino.scaffold.shiro.service.PasswordHelper;

/**
 * 用于employee登录
 * 
 * @author vino007
 *
 */
@Controller
public class EmployeeLoginController {

	@Autowired
	private PasswordHelper passwordHelper;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	@Qualifier("redisTokenManager")
	private TokenManager tokenManager;
	@RequestMapping(value = "/api/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(String username, String password, @RequestParam(required = false) String cid) {

		Map<String, Object> result = new HashMap<String, Object>();
		Employee realEmployee = employeeService.findByUsername(username);
		if (realEmployee == null) {
			result.put("result", "failed");
			return result;
		}
		boolean isSuccess = passwordHelper.judgeEmployeePassword(password, realEmployee.getPassword(),
				realEmployee.getSalt());
		if (isSuccess) {
			TokenModel tokenModel=tokenManager.createToken(realEmployee.getId());
			result.put(AuthenticationConstants.AUTHENTICATION, tokenModel.getToken());
			if (null != cid && !"".equals(cid))
				employeeService.updateCid(cid, username);// 更新CID

			result.put("result", "success");
			result.put("employee", realEmployee);

		} else {
			result.put("result", "failed");
		}
		return result;
	}
}
