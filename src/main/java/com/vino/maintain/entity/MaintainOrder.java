package com.vino.maintain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.vino.scaffold.entity.base.BaseEntity;
@Table(name="t_maintain_order")
@Entity
public class MaintainOrder extends BaseEntity<Long> {
	@Column(length=30)
	private String no;//������ţ����ɸ�ʽ
	@Column(name="final_Time")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date finalTime;//��ֹ����
	@Column(name="receiving_time")
	private Date receivingTime;//�ӵ�ʱ��
	@Column(name="sign_in_time")
	private Date signInTime;//ǩ��ʱ��
	@Column(name="sign_out_time")
	private Date signOutTime;//ǩ��ʱ��
	@Column(name="sign_in_address",length=100)
	private String signInAddress;//ǩ���ص�
	@Column(name="sign_out_address",length=100)
	private String signOutAddress;//ǩ�˵ص�
	@Column(name="is_expire")
	private Boolean isExpire=false;//�Ƿ���δ���
	private Boolean finished=Boolean.FALSE;//�Ƿ����
	@Column(length=300)
	private String remark;//��ע
	@Column(name="finished_items")
	private String finishedItems;//id1;id2;id3
	
/*	@ManyToOne(fetch=FetchType.LAZY)
	private Group group;*/
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Employee employee;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private ElevatorRecord elevatorRecord;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private MaintainType maintainType;

	public Date getFinalTime() {
		return finalTime;
	}
	public void setFinalTime(Date finalTime) {
		this.finalTime = finalTime;
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
	public Boolean getFinished() {
		return finished;
	}
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/*public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}*/
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
	public MaintainType getMaintainType() {
		return maintainType;
	}
	public void setMaintainType(MaintainType maintainType) {
		this.maintainType = maintainType;
	}
	public Boolean getIsExpire() {
		return isExpire;
	}
	public void setIsExpire(Boolean isExpire) {
		this.isExpire = isExpire;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getFinishedItems() {
		return finishedItems;
	}
	public void setFinishedItems(String finishedItems) {
		this.finishedItems = finishedItems;
	}
	
	
}
