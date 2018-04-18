package com.vino.maintain.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vino.maintain.entity.Group;
import com.vino.maintain.exception.GroupDuplicateException;
import com.vino.scaffold.service.base.BaseService;

public interface GroupService extends BaseService<Group, Long> {
	public Page<Group> findGroupByCondition(Map<String,Object> searchParams,Pageable pageable);

	public void saveWithCheckDuplicate(Group group) throws GroupDuplicateException;

}
