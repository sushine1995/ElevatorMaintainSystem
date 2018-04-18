package com.vino.maintain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.vino.maintain.entity.MaintainOrder;
import com.vino.scaffold.repository.base.BaseRepository;

public interface MaintainOrderRepository extends BaseRepository<MaintainOrder, Long>{
	@Query("select m.elevatorRecord.group.id from MaintainOrder m where m.finished=false and m.maintainType.name=?1")
	List<Long> notFinishedOrder(String maintainTypeName);
}
