package com.vino.maintain.service.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;

public class PushToSingleThread implements Runnable{
	 private final Logger log=LoggerFactory.getLogger(this.getClass());
	 private SingleMessage message;
	 private IGtPush push;
	 private Target target;
	 public PushToSingleThread(SingleMessage message,IGtPush push, Target target) {
		 this.message=message;
		 this.push=push;
		 this.target=target;
	 }
	@Override
	public void run() {
		 IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
            
        }
        if (ret != null) {
       	 log.info(ret.getResponse().toString());
        } else {
            log.info("服务器响应异常");
        }
	}
	
}