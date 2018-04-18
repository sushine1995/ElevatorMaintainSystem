package com.vino.maintain.service.notice;

import java.util.Map;

import com.vino.maintain.entity.notice.EmployeeNotice;
import com.vino.scaffold.service.base.BaseService;

public interface EmployeeNoticeService extends BaseService<EmployeeNotice, Long>{
	/**
	 * ���͹��Ϲ���
	 * @param groupId
	 * @param  faultOrderId
	 */
	void pushFaultOrderNotice(Long groupId,Long faultOrderId);
	/**
	 * ���ͱ�������
	 * @param groupId
	 * @param maintainOrderId
	 */
	void pushMaintainOrderNotice(Long groupId,Long maintainOrderId);
	/**
	 * ����������ֹ���ڽ���ʱ����֪ͨ�û�
	 * @param countMap
	 */
	void pushMaintainDeadlineNotice(Map<Long,Integer> countMap);
	/**
	 * ���͸�app
	 * @param title
	 * @param content
	 */
	void pushApp(String title,String content);
	/**
	 * ���͸�ѡ�е�group
	 * @param groupIds
	 * @param title
	 * @param content
	 */
	void pushGroups(Long[] groupIds,String title,String content);
}
