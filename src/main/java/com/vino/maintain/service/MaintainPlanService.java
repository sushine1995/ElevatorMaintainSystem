package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.MaintainPlan;
import com.vino.maintain.exception.MaintainPlanDuplicateException;
import com.vino.scaffold.service.base.BaseService;

public interface MaintainPlanService extends BaseService<MaintainPlan, Long>{
	Page<MaintainPlan> findMaintainPlanByCondition(Map<String, Object> searchParams, Pageable buildPageRequest);

	void saveWithCheckDuplicate(MaintainPlan maintainPlan) throws MaintainPlanDuplicateException;


}
