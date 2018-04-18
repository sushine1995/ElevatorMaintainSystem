package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.ElevatorRecord;
import com.vino.scaffold.service.base.BaseService;

public interface ElevatorRecordService extends BaseService<ElevatorRecord, Long>{
	public Page<ElevatorRecord> findElevatorRecordByCondition(Map<String,Object> searchParams,Pageable pageable);
	public void saveWithCreateTimeAndCreator(ElevatorRecord elveatorRecord);
	void bindMaintainPlan(long[] elevatorRecordIds,long maintainPlanId);
	Page<ElevatorRecord> findElevatorRecordByGroup(Pageable pageable, Long groupId);
	/**
	 * 为移动端提供模糊查询
	 * @param param 查询参数
	 * @param pageable
	 * @return
	 */
	Page<ElevatorRecord> findElevatorRecordByCondition(String param,Pageable pageable);
}
