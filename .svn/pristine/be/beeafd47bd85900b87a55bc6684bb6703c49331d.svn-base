package com.vino.maintain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.vino.scaffold.entity.base.BaseEntity;
@Entity
@Table(name="t_group")
public class Group extends BaseEntity<Long>{
	@Column(name="name",length=50,nullable=false,unique=true)
	private String name;
	@Column(name="description",length=300)
	private String description;
	
	@JSONField(serialize=false)
	@OneToMany(mappedBy="group",fetch=FetchType.LAZY)
	private List<Employee> employees;
	
	@JSONField(serialize=false)
	@OneToMany(mappedBy="group",fetch=FetchType.LAZY)
	private List<ElevatorRecord> elevatorRecords;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<ElevatorRecord> getElevatorRecords() {
		return elevatorRecords;
	}
	public void setElevatorRecords(List<ElevatorRecord> elevatorRecords) {
		this.elevatorRecords = elevatorRecords;
	}

}
