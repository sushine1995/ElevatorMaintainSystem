package com.vino.maintain.service.notice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.maintain.entity.FaultOrder;
import com.vino.maintain.entity.notice.UserNotice;
import com.vino.maintain.repository.notice.UserNoticeRepository;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;
@Service("userNoticeService")
@Transactional
public class UserNoticeServiceImpl extends AbstractBaseServiceImpl<UserNotice, Long> implements UserNoticeService{
	@Autowired
	private  SimpMessagingTemplate template;	
	@Autowired
	private UserNoticeRepository userNoticeRepository;
	@Override
	public void sendFaultOrderFixedNotice(FaultOrder faultOrder){
		UserNotice notice=new UserNotice();
		notice.setType("故障工单");
		notice.setContent("编号为"+faultOrder.getNo()+"的故障工单已完成");
		log.info("编号为"+faultOrder.getNo()+"的故障工单已完成,准备发送通知");
		notice.setCreateTime(new Date());	
		userNoticeRepository.save(notice);
		template.convertAndSend("/notice/new", notice);//弹窗提醒新消息
		//更新消息窗口和消息条数
		List<UserNotice> notices=findNoReadNotices();
		Map<String,Object> result=new HashMap<>();
		result.put("noReadCount",notices.size());
		result.put("noReadNotices", notices);
		
		template.convertAndSend("/notice/count", result);//convert会自动将数据转换成json格式,更新消息条数角标		
	}
	
	@Override
	public Long getNoReadNoticeCount() {
		
		return userNoticeRepository.getNoReadNoticeCount();
	}

	@Override
	public List<UserNotice> findNoReadNotices() {
		
		return userNoticeRepository.findUserNoticesByIsReadOrderByCreateTime(false);
	}

	@Override
	public List<UserNotice> findReadNotices() {
		// TODO Auto-generated method stub
		return userNoticeRepository.findUserNoticesByIsReadOrderByCreateTime(true);
	}
	@Override
	public void update(UserNotice obj) {
		UserNotice obj2=userNoticeRepository.findOne(obj.getId());

		try {
			@SuppressWarnings("unchecked")
			Class<UserNotice> clazz=(Class<UserNotice>) Class.forName("com.vino.maintain.entity.UserNotice");
			Method[] methods=clazz.getDeclaredMethods();
			for(Method m:methods){
				 if(m.getName().substring(0, 3).equals("get")){					
					Object value=m.invoke(obj);
					if(value!=null){
					Method setMethod=clazz.getDeclaredMethod("set"+m.getName().substring(3, 4).toUpperCase()+m.getName().substring(4),m.getReturnType());
					setMethod.invoke(obj2, value);
					}
				}		
			}
						
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setNoticeIsRead(long id) {
		userNoticeRepository.setUserNoticeIsRead(id);
		
	}
	
	@Override
	public Page<UserNotice> findNoReadNotices(Pageable pageable) {

        Specification<UserNotice> spec = new Specification<UserNotice>(){           
			@Override
			public Predicate toPredicate(Root<UserNotice> root,
				CriteriaQuery<?> cq, CriteriaBuilder cb) {				
				Predicate condition=cb.equal(root.get("isRead").as(Boolean.class),false);				
				return condition;
			}
        	
        };
		return userNoticeRepository.findAll(spec, pageable);
	}

	@Override
	public Page<UserNotice> findReadNotices(Pageable pageable) {
		  Specification<UserNotice> spec = new Specification<UserNotice>(){           
				@Override
				public Predicate toPredicate(Root<UserNotice> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {				
					Predicate condition=cb.equal(root.get("isRead").as(Boolean.class),true);				
					return condition;
				}
	        	
	        };
			return userNoticeRepository.findAll(spec, pageable);
	}


}
