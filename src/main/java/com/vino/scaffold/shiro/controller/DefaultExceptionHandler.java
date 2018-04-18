package com.vino.scaffold.shiro.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.fastjson.JSON;
import com.vino.scaffold.shiro.exception.CurrentUserNoExistException;

@ControllerAdvice
public class DefaultExceptionHandler {
	@ExceptionHandler({CurrentUserNoExistException.class})
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String CurrentUserNoExistException(NativeWebRequest request, CurrentUserNoExistException e) {
		Logger log=LoggerFactory.getLogger(this.getClass());
	    log.info("CurrentUserNoExistException·¢Éú");
		//boolean isAjax=isAjax(request);
	    return "login";
       
    }
	public boolean isAjax(NativeWebRequest request){
		String requestWith=request.getHeader("X-Requested-With");
		if(null!=requestWith&&"XMLHttpRequest".equals(requestWith))
			return true;
		else
			return false;
	}
}
