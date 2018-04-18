package com.vino.maintain.service.push;



import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.ITemplate;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.vino.maintain.common.NoticeConstants;
import com.vino.scaffold.entity.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 消息推送,spring默认scope就是单例模式,可以通过@scope更改
 * @author vino007
 *
 */
@Service
public class AppPush {
	@Resource(name="appPushExecutor")
	private ThreadPoolTaskExecutor appPushExecutor;//获取推送用的线程池
	private  final Logger log=LoggerFactory.getLogger(this.getClass());
    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
	private  String appId = NoticeConstants.appId;
    private  String appKey = NoticeConstants.appKey;
    private  String masterSecret = NoticeConstants.masterSecret;
    private  String host = NoticeConstants.host;//推送os域名, host可选填，如果host不填程序自动检测用户网络，选择最快的域名连接下发。
  
    /**
     * 推送给所有用户
     * @param title
     * @param content
     */
    public  void push(String title,String content){
        IGtPush push = new IGtPush(host, appKey, masterSecret);//创建一个个推App对象
        try {
			push.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle(title);
        template.setText(content);
        template.setUrl("http://getui.com");

        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
       
    }
    /**
     * 推送给单个用户
     * @param CID
     * @param title
     * @param content
     * @param payload 透明传输的内容
     */
    public  void pushToSingle(String CID,String title,String content,String payload) {
    	 IGtPush push = new IGtPush(host, appKey, masterSecret);
         ITemplate template = getNotificationTemplate(title,content,payload);
         SingleMessage message = new SingleMessage();
        
         message.setOffline(true);
         // 离线有效时间，单位为毫秒，可选
         message.setOfflineExpireTime(24 * 3600 * 1000);
         message.setData(template);
         // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
         message.setPushNetWorkType(0); 
         Target target = new Target();
         target.setAppId(appId);
         target.setClientId(CID);
         //target.setAlias(Alias);
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
    /**
     * 使用线程来对单个用户发送消息
     * @param CID
     * @param title
     * @param content
     * @param payload
     */
    public void pushToSingleAsync(String CID,String title,String content,String payload){
    	IGtPush push = new IGtPush(host, appKey, masterSecret);
        ITemplate template = getNotificationTemplate(title,content,payload);
        SingleMessage message = new SingleMessage();
       
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
        //target.setAlias(Alias);
        appPushExecutor.execute(new PushToSingleThread(message, push, target));
    }
    
    public void pushToAppAsync(String title,String content,String payload){
    	IGtPush push = new IGtPush(host, appKey, masterSecret);
        ITemplate template = getNotificationTemplate(title,content,payload);
        AppMessage message = new AppMessage();
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        message.setAppIdList(appIds);
        //target.setAlias(Alias);
        appPushExecutor.execute(new PushToAppThread(message, push));
    }
   
    /**
     * 获取通知模板
     * @param title
     * @param content
     * @param payload
     * @return
     */
    public  NotificationTemplate getNotificationTemplate(String title,String content,String payload){
    	 NotificationTemplate template = new NotificationTemplate();
 	    // 设置APPID与APPKEY
 	    template.setAppId(appId);
 	    template.setAppkey(appKey);
 	    // 设置通知栏标题与内容
 	    template.setTitle(title);
 	    template.setText(content);
 	    // 配置通知栏图标
 	    template.setLogo("elevator.png");
 	    // 配置通知栏网络图标
 	    template.setLogoUrl("");
 	    // 设置通知是否响铃，震动，或者可清除
 	    template.setIsRing(true);
 	    template.setIsVibrate(true);
 	    template.setIsClearable(true);
 	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
 	    template.setTransmissionType(2);
 	    template.setTransmissionContent(payload);
 	    // 设置定时展示时间
 	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
 	    return template;
    	
    }
    
    
    public  TransmissionTemplate getTransmissionTemplate(String title,String content) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(2);
	    template.setTransmissionContent(content);
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
}