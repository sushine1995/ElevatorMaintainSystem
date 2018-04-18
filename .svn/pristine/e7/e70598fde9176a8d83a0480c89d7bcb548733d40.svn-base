package com.vino.maintain.service.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.vino.maintain.common.NoticeConstants;
import com.vino.maintain.entity.Employee;
import com.vino.maintain.entity.notice.EmployeeNotice;
import com.vino.maintain.repository.EmployeeRepository;
import com.vino.maintain.repository.notice.EmployeeNoticeRepository;
import com.vino.maintain.service.push.AppPush;
import com.vino.scaffold.service.base.AbstractBaseServiceImpl;

@Service("employeeNoticeService")
@Transactional
public class EmployeeNoticeServiceImpl extends AbstractBaseServiceImpl<EmployeeNotice, Long>
		implements EmployeeNoticeService {
	@Autowired
	private AppPush appPush;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeNoticeRepository employeeNoticeRepository;

	@Override
	public void pushFaultOrderNotice(Long groupId,Long faultOrderId) {
		if (groupId == null)
			throw new NullPointerException("groupId 为null");
		if (groupId <= 0)
			throw new IllegalArgumentException("groupId 应为正数");

		List<Employee> employees = employeeRepository.findEmployeesByGroup(groupId);
		if (null == employees || employees.size() == 0)
			return;
		for (Employee e : employees) {
			EmployeeNotice notice = new EmployeeNotice();
			notice.setTitle(NoticeConstants.FAULT_ORDER_TITLE);
			notice.setContent(NoticeConstants.FAULT_ORDER_CONTENT);
			notice.setReceiver(e);
			notice.setCreateTime(new Date());
			employeeNoticeRepository.save(notice);// 保存消息到数据库
			String CID = e.getCid();
			if(null!=CID&&!"".equals(CID)){
				
				appPush.pushToSingleAsync(CID, notice.getTitle(), notice.getContent(),getFaultPayload(faultOrderId));
				log.info("推送一条故障通知给:" + e.getUsername());
			}
			
		}

	}
	/**
	 * 生成故障工单透明传输的数据
	 * @param faultOrderId
	 * @return
	 */
	private final String getFaultPayload(Long faultOrderId){
		Map<String,Object> payload=new HashMap<>();
		payload.put("id", faultOrderId);
		payload.put("orderType", "faultOrder");
		return JSON.toJSONString(payload);
	}
	/**
	 * 生成保养工单透明传输数据
	 * @return
	 */
	private final String getMaintainPayload( ){
		Map<String,Object> payload=new HashMap<>();
		payload.put("id", "");
		payload.put("orderType", "maintainOrder");
		return JSON.toJSONString(payload);
	}
	
	@Override
	public void pushMaintainOrderNotice(Long groupId, Long maintainOrderId) {
		if (groupId == null)
			throw new NullPointerException("groupId 为null");
		if (groupId <= 0)
			throw new IllegalArgumentException("groupId 应为正数");

		List<Employee> employees = employeeRepository.findEmployeesByGroup(groupId);
		if (null == employees || employees.size() == 0)
			return;
		for (Employee e : employees) {
			EmployeeNotice notice = new EmployeeNotice();
			notice.setTitle(NoticeConstants.MAINTAIN_ORDER_TITLE);
			notice.setContent(NoticeConstants.MAINTAIN_ORDER_CONTENT);
			notice.setReceiver(e);
			notice.setCreateTime(new Date());
			employeeNoticeRepository.save(notice);// 保存消息到数据库
			String CID = e.getCid();
			if(null!=CID&&!"".equals(CID)){
				appPush.pushToSingleAsync(CID, notice.getTitle(), notice.getContent(),getMaintainPayload());
				log.info("推送一条保养通知给:" + e.getUsername());
			}
			
		}
		
	}
	@Override
	public void pushMaintainDeadlineNotice(Map<Long, Integer> countMap) {
		// TODO Auto-generated method stub
		if (countMap == null)
			throw new NullPointerException("countMap 为null");
		
		for(Map.Entry<Long, Integer> entry:countMap.entrySet()){
			long groupId=entry.getKey();
			int count=entry.getValue();
			pushDeadlineCount(groupId, count);
		}
		
	}
	
	private void pushDeadlineCount(Long groupId,Integer count){
		if (groupId == null)
			throw new NullPointerException("groupId 为null");
		if (groupId <= 0)
			throw new IllegalArgumentException("groupId 应为正数");

		List<Employee> employees = employeeRepository.findEmployeesByGroup(groupId);
		if (null == employees || employees.size() == 0)
			return;
		for (Employee e : employees) {
			EmployeeNotice notice = new EmployeeNotice();
			notice.setTitle(NoticeConstants.DEADLINE_TITLE);
			notice.setContent("您还有"+count+"个保养工单未完成，请在截止日期前尽快处理");
			notice.setReceiver(e);
			notice.setCreateTime(new Date());
			employeeNoticeRepository.save(notice);// 保存消息到数据库
			String CID = e.getCid();
			if(null!=CID&&!"".equals(CID)){
				appPush.pushToSingleAsync(CID, notice.getTitle(), notice.getContent(),null);
				log.info("推送一条截止通知给:" + e.getUsername());
			}
			
		}
	}
	@Override
	public void pushApp(String title, String content) {
		// TODO Auto-generated method stub
		appPush.pushToAppAsync(title, content, null);
	}
	@Override
	public void pushGroups(Long[] groupIds, String title, String content) {
		if(groupIds==null) return ;
		List<Employee> employees=new ArrayList<>();
		for(long groupId:groupIds){
			employees.addAll(employeeRepository.findEmployeesByGroup(groupId));
		}
		 
		if (null == employees || employees.size() == 0)
			return;
		for (Employee e : employees) {
			EmployeeNotice notice = new EmployeeNotice();
			notice.setTitle(title);
			notice.setContent(content);
			notice.setReceiver(e);
			notice.setCreateTime(new Date());
			employeeNoticeRepository.save(notice);// 保存消息到数据库
			String CID = e.getCid();
			if(null!=CID&&!"".equals(CID)){
				
				appPush.pushToSingleAsync(CID, notice.getTitle(), notice.getContent(),null);
				log.info("推送一条通知给:" + e.getUsername());
			}
			
		}
		
	}
	

}
