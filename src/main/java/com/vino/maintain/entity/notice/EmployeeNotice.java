package com.vino.maintain.entity.notice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vino.maintain.entity.Employee;
import com.vino.scaffold.entity.base.BaseEntity;
import com.vino.scaffold.shiro.entity.User;
@Entity
@Table(name="t_employee_notice")
public class EmployeeNotice extends BaseEntity<Long>{
	@Column(length=20)
	private String type;
	@ManyToOne
	private Employee receiver;
	private Boolean isRead=Boolean.FALSE;
	@Column(length=30)
	private String title;

	@Column(length=400)
	private String content;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Employee getReceiver() {
		return receiver;
	}

	public void setReceiver(Employee receiver) {
		this.receiver = receiver;
	}

	

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
