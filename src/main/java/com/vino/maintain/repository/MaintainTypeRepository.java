package com.vino.maintain.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import com.vino.maintain.entity.MaintainType;
import com.vino.scaffold.repository.base.BaseRepository;

public interface MaintainTypeRepository extends BaseRepository<MaintainType, Long> {
	MaintainType findByName(String name);
	
}
