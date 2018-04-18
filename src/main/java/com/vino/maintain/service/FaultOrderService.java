package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.FaultOrder;
import com.vino.scaffold.service.base.BaseService;

public interface FaultOrderService extends BaseService<FaultOrder, Long>{
	/**
	 * 创建故障工单，并推送通知
	 * @param faultOrder
	 */
	void saveWithCreateTimeAndCreator(FaultOrder faultOrder);

	Page<FaultOrder> findFaultOrderByCondition(Map<String, Object> searchParams, Pageable pageable);
	Page<FaultOrder> findFixedOrder(Pageable pageable);
	Page<FaultOrder> findNotFixedOrder(Pageable pageable);
	/**
	 * 接单
	 * @param id 工单id
	 * @param employeeId 员工id
	 * @return success repeat faultOrderNotExist employeeNotExist
	 */
	String acceptOrder(long id,long employeeId);
	/**
	 * 取消订单
	 * @param id	工单id
	 * @param employeeId 员工id
	 * @return success 成功 notAccept员工没有该工单 notMatch faultOrderNotExist employeeNotExist
	 */
	String cancelOrder(long id, long employeeId);
	/**
	 * 员工签到
	 * @param id 工单id
	 * @param signInAddress 签到地址
	 * @return success faultOrderNotExist 
	 */
	String signIn(long id, String signInAddress);
	/**
	 * 查询小组未完成的任务
	 * @param pageable
	 * @param groupId
	 * @return Page类型的FaultOrder列表
	 */
	Page<FaultOrder> findNotFixedOrderByGroup(Pageable pageable, long groupId);
	/**
	 * 查询小组完成的任务
	 * @param pageable
	 * @param groupId
	 * @return Page类型的FaultOrder列表
	 */
	Page<FaultOrder> findFixedOrderByGroup(Pageable pageable, long groupId);
	/**
	 * 根据用户id查找已接单未完成的任务
	 * @param pageable
	 * @param employeeId
	 * @return Page类型的FaultOrder列表
	 */
	Page<FaultOrder> findAcceptedOrder(Pageable pageable, long employeeId);
	/**
	 * 查询已签到的工单
	 * @param pageable
	 * @param employeeId
	 * @return Page类型的FaultOrder列表
	 */
	Page<FaultOrder> findSignedOrder(Pageable pageable, long employeeId);
	/**
	 * 反馈工单
	 * @param faultOrder
	 */
	void feedback(FaultOrder faultOrder);
	Page<FaultOrder> findFaultHistory(Pageable pageable,long elevatorRecordId);
}
