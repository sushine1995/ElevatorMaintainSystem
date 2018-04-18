package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.MaintainItem;
import com.vino.maintain.exception.MaintainItemDuplicateException;
import com.vino.scaffold.service.base.BaseService;

public interface MaintainItemService extends BaseService<MaintainItem, Long>{

	Page<MaintainItem> findMaintainItemByCondition(Map<String, Object> searchParams, Pageable pageable);

	void saveWithCheckDuplicate(MaintainItem maintainItem) throws MaintainItemDuplicateException;
	public boolean bindType(long maintainItemId,long maintainTypeId);

}
