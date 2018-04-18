package com.vino.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.vino.scaffold.entity.base.BaseEntity;
@Entity
@Table(name="t_employee")
public class Employee extends BaseEntity<Long>{
	@Column(name = "cid",length=100)
	@JSONField(serialize=false)
	private String cid;//¸öÍÆÖÐÓÃÀ´±êÊ¶Ã¿¸ö¿Í»§¶ËµÄµÄid
	@JSONField(serialize=false)
	@Column(name = "salt",length=50)
	private String salt;
	@JSONField(serialize=false)
	@Column(name = "locked")
	private Boolean locked = Boolean.FALSE;
	@Column(name="username",length=30,nullable=false,unique=true)
	private String username;
	@Column(name="name",length=20,nullable=false)
	private String name;
	@JSONField(serialize=false)
	@Column(name="password",length=50,nullable=false)
	private String password;
	@Column(name="phone",length=15,nullable=false)
	private String phone;
	@ManyToOne(fetch=FetchType.LAZY)
	private Group group;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}

}
