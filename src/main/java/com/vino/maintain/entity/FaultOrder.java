package com.vino.maintain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.vino.scaffold.entity.base.BaseEntity;
@Entity
@Table(name="t_fault_order")
public class FaultOrder extends BaseEntity<Long>{
	@Column(length=30)
	private String no;//订单编号
	@Column(name="occured_time")
	private Date occuredTime;//故障发生时间
	@Column(length=300)
	private String description;//故障描述
	@Column(length=300)
	private String reason;//故障原因
	@Column(name="receiving_time")
	private Date receivingTime;
	@Column(name="sign_in_time")
	private Date signInTime;
	@Column(name="sign_out_time")
	private Date signOutTime;
	@Column(name="sign_in_address",length=100)
	private String signInAddress;
	@Column(name="sign_out_address",length=100)
	private String signOutAddress;
	
	private Boolean fixed=Boolean.FALSE;
	@Column(length=300)
	private String remark;//备注

	@ManyToOne(fetch=FetchType.LAZY)
	private Employee employee;//维修的人
	@ManyToOne(fetch=FetchType.LAZY)
	private ElevatorRecord elevatorRecord;
	/*@ManyToOne(fetch=FetchType.LAZY)
	private Group group;
	
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}*/
	public Date getOccuredTime() {
		return occuredTime;
	}
	public void setOccuredTime(Date occuredTime) {
		this.occuredTime = occuredTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getReceivingTime() {
		return receivingTime;
	}
	public void setReceivingTime(Date receivingTime) {
		this.receivingTime = receivingTime;
	}
	public Date getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}
	public Date getSignOutTime() {
		return signOutTime;
	}
	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}
	public String getSignInAddress() {
		return signInAddress;
	}
	public void setSignInAddress(String signInAddress) {
		this.signInAddress = signInAddress;
	}
	public String getSignOutAddress() {
		return signOutAddress;
	}
	public void setSignOutAddress(String signOutAddress) {
		this.signOutAddress = signOutAddress;
	}
	public Boolean getFixed() {
		return fixed;
	}
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public ElevatorRecord getElevatorRecord() {
		return elevatorRecord;
	}
	public void setElevatorRecord(ElevatorRecord elevatorRecord) {
		this.elevatorRecord = elevatorRecord;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
}
