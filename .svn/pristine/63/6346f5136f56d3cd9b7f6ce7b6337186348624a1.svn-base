package com.vino.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.alibaba.fastjson.annotation.JSONField;
import com.vino.scaffold.entity.base.BaseEntity;
@Table(name="t_maintain_item")
@Entity
public class MaintainItem extends BaseEntity<Long> {
	@Column(length=30,unique=true)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JSONField(serialize=false)
	private MaintainType maintainType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MaintainType getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(MaintainType maintainType) {
		this.maintainType = maintainType;
	}
	

}
