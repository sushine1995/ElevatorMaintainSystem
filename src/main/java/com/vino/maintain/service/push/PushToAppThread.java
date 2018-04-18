package com.vino.maintain.service.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;

public class PushToAppThread implements Runnable {
	 private final Logger log=LoggerFactory.getLogger(this.getClass());
	 private AppMessage message;
	 private IGtPush push;
	 public PushToAppThread(AppMessage message,IGtPush push) {
		 this.message=message;
		 this.push=push;
	 }
	@Override
	public void run() {
		 IPushResult ret = null;
        try {
            ret = push.pushMessageToApp(message);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToApp(message);
            
        }
        if (ret != null) {
       	 log.info(ret.getResponse().toString());
        } else {
            log.info("服务器响应异常");
        }
	}
}
