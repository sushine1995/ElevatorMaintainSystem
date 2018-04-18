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
 * ��Ϣ����,springĬ��scope���ǵ���ģʽ,����ͨ��@scope����
 * @author vino007
 *
 */
@Service
public class AppPush {
	@Resource(name="appPushExecutor")
	private ThreadPoolTaskExecutor appPushExecutor;//��ȡ�����õ��̳߳�
	private  final Logger log=LoggerFactory.getLogger(this.getClass());
    //���峣��, appId��appKey��masterSecret ���ñ��ĵ� "�ڶ��� ��ȡ����ƾ֤ "�л�õ�Ӧ������
	private  String appId = NoticeConstants.appId;
    private  String appKey = NoticeConstants.appKey;
    private  String masterSecret = NoticeConstants.masterSecret;
    private  String host = NoticeConstants.host;//����os����, host��ѡ����host��������Զ�����û����磬ѡ���������������·���
  
    /**
     * ���͸������û�
     * @param title
     * @param content
     */
    public  void push(String title,String content){
        IGtPush push = new IGtPush(host, appKey, masterSecret);//����һ������App����
        try {
			push.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        // ����"������Ӵ�֪ͨģ��"�������ñ��⡢���ݡ�����
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle(title);
        template.setText(content);
        template.setUrl("http://getui.com");

        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // ����"AppMessage"������Ϣ����������Ϣ����ģ�塢���͵�Ŀ��App�б��Ƿ�֧�����߷��͡��Լ�������Ϣ��Ч��(��λ����)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
       
    }
    /**
     * ���͸������û�
     * @param CID
     * @param title
     * @param content
     * @param payload ͸�����������
     */
    public  void pushToSingle(String CID,String title,String content,String payload) {
    	 IGtPush push = new IGtPush(host, appKey, masterSecret);
         ITemplate template = getNotificationTemplate(title,content,payload);
         SingleMessage message = new SingleMessage();
        
         message.setOffline(true);
         // ������Чʱ�䣬��λΪ���룬��ѡ
         message.setOfflineExpireTime(24 * 3600 * 1000);
         message.setData(template);
         // ��ѡ��1Ϊwifi��0Ϊ���������绷���������ֻ����ڵ���������������Ƿ��·�
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
             log.info("��������Ӧ�쳣");
         }
    }
    /**
     * ʹ���߳����Ե����û�������Ϣ
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
        // ������Чʱ�䣬��λΪ���룬��ѡ
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // ��ѡ��1Ϊwifi��0Ϊ���������绷���������ֻ����ڵ���������������Ƿ��·�
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
        // ������Чʱ�䣬��λΪ���룬��ѡ
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // ��ѡ��1Ϊwifi��0Ϊ���������绷���������ֻ����ڵ���������������Ƿ��·�
        message.setPushNetWorkType(0); 
        message.setAppIdList(appIds);
        //target.setAlias(Alias);
        appPushExecutor.execute(new PushToAppThread(message, push));
    }
   
    /**
     * ��ȡ֪ͨģ��
     * @param title
     * @param content
     * @param payload
     * @return
     */
    public  NotificationTemplate getNotificationTemplate(String title,String content,String payload){
    	 NotificationTemplate template = new NotificationTemplate();
 	    // ����APPID��APPKEY
 	    template.setAppId(appId);
 	    template.setAppkey(appKey);
 	    // ����֪ͨ������������
 	    template.setTitle(title);
 	    template.setText(content);
 	    // ����֪ͨ��ͼ��
 	    template.setLogo("elevator.png");
 	    // ����֪ͨ������ͼ��
 	    template.setLogoUrl("");
 	    // ����֪ͨ�Ƿ����壬�𶯣����߿����
 	    template.setIsRing(true);
 	    template.setIsVibrate(true);
 	    template.setIsClearable(true);
 	    // ͸����Ϣ���ã�1Ϊǿ������Ӧ�ã��ͻ��˽��յ���Ϣ��ͻ���������Ӧ�ã�2Ϊ�ȴ�Ӧ������
 	    template.setTransmissionType(2);
 	    template.setTransmissionContent(payload);
 	    // ���ö�ʱչʾʱ��
 	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
 	    return template;
    	
    }
    
    
    public  TransmissionTemplate getTransmissionTemplate(String title,String content) {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    // ͸����Ϣ���ã�1Ϊǿ������Ӧ�ã��ͻ��˽��յ���Ϣ��ͻ���������Ӧ�ã�2Ϊ�ȴ�Ӧ������
	    template.setTransmissionType(2);
	    template.setTransmissionContent(content);
	    // ���ö�ʱչʾʱ��
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
}