package com.vino.maintain.service.notice;

import java.util.Map;

import com.vino.maintain.entity.notice.EmployeeNotice;
import com.vino.scaffold.service.base.BaseService;

public interface EmployeeNoticeService extends BaseService<EmployeeNotice, Long>{
	/**
	 * 推送故障工单
	 * @param groupId
	 * @param  faultOrderId
	 */
	void pushFaultOrderNotice(Long groupId,Long faultOrderId);
	/**
	 * 推送保养工单
	 * @param groupId
	 * @param maintainOrderId
	 */
	void pushMaintainOrderNotice(Long groupId,Long maintainOrderId);
	/**
	 * 保养工单截止日期将近时推送通知用户
	 * @param countMap
	 */
	void pushMaintainDeadlineNotice(Map<Long,Integer> countMap);
	/**
	 * 推送给app
	 * @param title
	 * @param content
	 */
	void pushApp(String title,String content);
	/**
	 * 推送给选中的group
	 * @param groupIds
	 * @param title
	 * @param content
	 */
	void pushGroups(Long[] groupIds,String title,String content);
}
