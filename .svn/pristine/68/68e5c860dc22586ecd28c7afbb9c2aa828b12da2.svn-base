package com.vino.maintain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.vino.maintain.entity.MaintainPlan;
import com.vino.scaffold.repository.base.BaseRepository;

public interface MaintainPlanRepository extends BaseRepository<MaintainPlan, Long>{
	MaintainPlan findByName(String name);
	//查询有效的保养计划(未过期)
	@Query("from MaintainPlan m where m.endDate >= ?1 ")
	List<MaintainPlan> findAvailalbeMaintainPlans(Date current);
}
