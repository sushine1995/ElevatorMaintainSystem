package com.vino.maintain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.vino.scaffold.entity.base.BaseEntity;
@Entity
@Table(name="t_elevator_record")
public class ElevatorRecord  extends BaseEntity<Long>{
	@Column(length=30)
	private String no;
	@Column(length=100)
	private String address;//设备地址
	@Column(length=100)
	private String unit;//使用单位
	@Column(name="building_number",length=20)
	private String buildingNumber;//楼号
	@Column(name="elevator_number",length=20)
	private String elevatorNumber;//梯号
	@Column(length=20)
	private String type;//电梯类型
	@Column(name="model_number",length=20)
	private String modelNumber;//型号
	@Column(name="max_weight")
	private Integer maxWeight;//载重
	@Column(name="speed")
	private Float speed;//速度
	@Column(name="manufacturing_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date manufacturingDate;//生产日期
	@Column(name="inspection_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date inspectionDate;//年检日期
	@Column(name="last_maintain_time")
	private Date lastMaintainTime;//上次维护时间
	@Column(name="phone",length=20)
	private String phone;//单位联系电话
	
	@JSONField(serialize=false)
	@ManyToOne(fetch=FetchType.LAZY)
	private MaintainPlan maintainPlan;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Group group;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBuildingNumber() {
		return buildingNumber;
	}
	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public Date getManufacturingDate() {
		return manufacturingDate;
	}
	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}
	public Date getLastMaintainTime() {
		return lastMaintainTime;
	}
	public void setLastMaintainTime(Date lastMaintainTime) {
		this.lastMaintainTime = lastMaintainTime;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Integer getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(Integer maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Float getSpeed() {
		return speed;
	}
	public void setSpeed(Float speed) {
		this.speed = speed;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public MaintainPlan getMaintainPlan() {
		return maintainPlan;
	}
	public void setMaintainPlan(MaintainPlan maintainPlan) {
		this.maintainPlan = maintainPlan;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getElevatorNumber() {
		return elevatorNumber;
	}
	public void setElevatorNumber(String elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
	}
	public Date getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	
	

}
