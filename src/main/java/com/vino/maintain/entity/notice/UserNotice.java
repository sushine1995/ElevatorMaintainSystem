package com.vino.maintain.entity.notice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.vino.scaffold.entity.base.BaseEntity;
import com.vino.scaffold.shiro.entity.User;
@Entity
@Table(name="t_user_notice")
public class UserNotice extends BaseEntity<Long> {
	@Column(length=20)
	private String type;//维保快到期，故障未处理，维保过期未处理，维保计划到期
	private Boolean isRead=Boolean.FALSE;
	@Column(length=400)
	private String content;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	public String getContent() {
		return content;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
