package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.MaintainType;
import com.vino.maintain.exception.MaintainTypeDuplicateException;
import com.vino.scaffold.service.base.BaseService;

public interface MaintainTypeService extends BaseService<MaintainType, Long>{

	Page<MaintainType> findMaintainTypeByCondition(Map<String, Object> searchParams, Pageable pageable);

	void saveWithCheckDuplicate(MaintainType maintainType) throws MaintainTypeDuplicateException;

}
