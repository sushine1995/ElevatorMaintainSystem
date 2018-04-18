package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.MaintainOrder;
import com.vino.scaffold.service.base.BaseService;

public interface MaintainOrderService extends BaseService<MaintainOrder, Long>{

	void saveWithCreateTimeAndCreator(MaintainOrder maintainOrder);

	Page<MaintainOrder> findMaintainOrderByCondition(Map<String, Object> searchParams, Pageable pageable);
	/**
	 * �ӵ�
	 * @param id ����id
	 * @param employeeId Ա��id
	 * @return success repeat maintainOrderNotExist employeeNotExist
	 */
	String acceptOrder(long id, long employeeId);
	/**
	 * ȡ������
	 * @param id	����id
	 * @param employeeId Ա��id
	 * @return success �ɹ� notAcceptԱ��û�иù��� notMatch maintainOrderNotExist employeeNotExist
	 */
	String cancelOrder(long id, long employeeId);
	/**
	 * Ա��ǩ��
	 * @param id ����id
	 * @param signInAddress ǩ����ַ
	 * @return success faultOrderNotExist 
	 */
	String signIn(long id, String signInAddress);

	String feedback(MaintainOrder maintainOrder);
	/**
	 * ��ѯС�鳬�ڹ���
	 * @param pageable
	 * @param groupId
	 * @return Page���͵�FaultOrder�б�
	 */
	Page<MaintainOrder> findExpiredOrderByGroup(Pageable pageable, Long groupId);
	/**
	 * ��ѯС����ɵ�����
	 * @param pageable
	 * @param groupId
	 * @return Page���͵�FaultOrder�б�
	 */
	Page<MaintainOrder> findFinishedOrderByGroup(Pageable pageable, Long groupId);

	Page<MaintainOrder> findNotFinishedOrderByGroup(Pageable pageable, Long groupId);

	/**
	 * �����û�id�����ѽӵ�δ��ɵ�����
	 * @param pageable
	 * @param employeeId
	 * @return Page���͵�FaultOrder�б�
	 */
	Page<MaintainOrder> findAcceptedOrder(Pageable pageable, Long employeeId);
	/**
	 * ��ѯ��ǩ���Ĺ���
	 * @param pageable
	 * @param employeeId
	 * @return Page���͵�FaultOrder�б�
	 */
	Page<MaintainOrder> findSignedOrder(Pageable pageable, Long employeeId);

}
