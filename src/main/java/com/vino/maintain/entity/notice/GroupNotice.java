package com.vino.maintain.entity.notice;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.vino.maintain.entity.Group;
import com.vino.scaffold.entity.base.BaseEntity;
import com.vino.scaffold.shiro.entity.User;

public class GroupNotice extends BaseEntity<Long>{
	@Column(length=20)
	private String type;
	@ManyToOne
	private Group receiver;
	private Boolean isRead=Boolean.FALSE;
	private String title;
	@Column(length=400)
	private String content;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Group getReceiver() {
		return receiver;
	}

	public void setReceiver(Group receiver) {
		this.receiver = receiver;
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
