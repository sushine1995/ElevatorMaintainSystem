package com.vino.maintain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.vino.scaffold.entity.base.BaseEntity;
@Entity
@Table(name="t_maintain_plan")
public class MaintainPlan extends BaseEntity<Long>{
	@Column(length=30)
	private String no;//合同编号
	@Column(length=50)
	private String name;//合同名字
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;//合同到期时间
	@Column(name="is_expire")
	private Boolean isExpire=false;//合同是否过期
	private Boolean isEffectiveImmediate;
	@ManyToOne(fetch=FetchType.LAZY)
	private Group group;
	
	@JSONField(serialize=false)
	@OneToMany(mappedBy="maintainPlan",fetch=FetchType.LAZY)//mappedBy用在被拥有的一端，另一端是有维护权限
	private List<ElevatorRecord> elevatorRecords;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Boolean getIsEffectiveImmediate() {
		return isEffectiveImmediate;
	}
	public void setIsEffectiveImmediate(Boolean isEffectiveImmediate) {
		this.isEffectiveImmediate = isEffectiveImmediate;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public List<ElevatorRecord> getElevatorRecords() {
		return elevatorRecords;
	}
	public void setElevatorRecords(List<ElevatorRecord> elevatorRecords) {
		this.elevatorRecords = elevatorRecords;
	}
	public Boolean getIsExpire() {
		return isExpire;
	}
	public void setIsExpire(Boolean isExpire) {
		this.isExpire = isExpire;
	}
	
}
