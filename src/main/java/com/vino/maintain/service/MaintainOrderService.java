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
	 * 接单
	 * @param id 工单id
	 * @param employeeId 员工id
	 * @return success repeat maintainOrderNotExist employeeNotExist
	 */
	String acceptOrder(long id, long employeeId);
	/**
	 * 取消订单
	 * @param id	工单id
	 * @param employeeId 员工id
	 * @return success 成功 notAccept员工没有该工单 notMatch maintainOrderNotExist employeeNotExist
	 */
	String cancelOrder(long id, long employeeId);
	/**
	 * 员工签到
	 * @param id 工单id
	 * @param signInAddress 签到地址
	 * @return success faultOrderNotExist 
	 */
	String signIn(long id, String signInAddress);

	String feedback(MaintainOrder maintainOrder);
	/**
	 * 查询小组超期工单
	 * @param pageable
	 * @param groupId
	 * @return Page类型的FaultOrder列表
	 */
	Page<MaintainOrder> findExpiredOrderByGroup(Pageable pageable, Long groupId);
	/**
	 * 查询小组完成的任务
	 * @param pageable
	 * @param groupId
	 * @return Page类型的FaultOrder列表
	 */
	Page<MaintainOrder> findFinishedOrderByGroup(Pageable pageable, Long groupId);

	Page<MaintainOrder> findNotFinishedOrderByGroup(Pageable pageable, Long groupId);

	/**
	 * 根据用户id查找已接单未完成的任务
	 * @param pageable
	 * @param employeeId
	 * @return Page类型的FaultOrder列表
	 */
	Page<MaintainOrder> findAcceptedOrder(Pageable pageable, Long employeeId);
	/**
	 * 查询已签到的工单
	 * @param pageable
	 * @param employeeId
	 * @return Page类型的FaultOrder列表
	 */
	Page<MaintainOrder> findSignedOrder(Pageable pageable, Long employeeId);

}
