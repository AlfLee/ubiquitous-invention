package com.telecom.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 类hibernate 自动更新表结构或者创建表
 * 用户所有信息
 * @author Administrator
 *
 */
@Table(name="sys_user")
public class user_entity extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "id",type = MySqlTypeConstant.VARCHAR,length = 111,isKey = true)//isunique属性不要使用 切记！！
	private String id;
	
	@Column(name = "username",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String username;
	
	@Column(name = "grade",type = MySqlTypeConstant.INT,length = 11)
	private int grade;
	
	@Column(name = "password",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String password;
	
	@Column(name = "email",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String email;
	public user_entity() {}
	public user_entity(String username, String email, int grade, String password) {
		super();
		this.username = username;
		this.email = email;
		this.grade = grade;
		this.password = password;
	}
	
	public user_entity(String id, String username, String email, int grade, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.grade = grade;
		this.password = password;
	}
	public user_entity(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
