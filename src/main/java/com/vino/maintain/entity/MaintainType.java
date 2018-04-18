package com.vino.maintain.entity;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.alibaba.fastjson.annotation.JSONField;
import com.vino.scaffold.entity.base.BaseEntity;
@Table(name="t_maintain_type")
@Entity
public class MaintainType extends BaseEntity<Long> {
	@Column(length=30,unique=true)
	private String name;
	/*@JSONField(serialize=false)*/
	@OneToMany(mappedBy="maintainType",fetch=FetchType.LAZY)//mappedBy用在被拥有的一端，另一端是有维护权限
	private List<MaintainItem> maintainItems;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<MaintainItem> getMaintainItems() {
		return maintainItems;
	}
	public void setMaintainItems(List<MaintainItem> maintainItems) {
		this.maintainItems = maintainItems;
	}
	
}
