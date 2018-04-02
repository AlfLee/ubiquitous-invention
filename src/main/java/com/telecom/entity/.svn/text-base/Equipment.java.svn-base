package com.telecom.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 类hibernate 自动更新表结构或者创建表
 * 设备实体表
 * @author Administrator
 *
 */
@Table(name="equipment")
public class Equipment extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 设备id
	 */
	@Column(name = "id",type = MySqlTypeConstant.VARCHAR,length = 111,isKey=true)
	private String id;
	
	/**
	 * 设备名称
	 */
	@Column(name = "name",type = MySqlTypeConstant.VARCHAR, length=111)
	private String name;
	
	/**
	 * 父设备
	 */
	@Column(name = "parent_id",type = MySqlTypeConstant.VARCHAR, length=111)
	private String parentId;
	
	/**
	 * 创建人
	 */
	@Column(name = "created_user",type = MySqlTypeConstant.VARCHAR, length=111)
	private String createdUser;
	
	/**
	 * 修改时间
	 */
	@Column(name = "modify_time",type = MySqlTypeConstant.BIGINT)
	private long modifyTime;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time",type = MySqlTypeConstant.BIGINT)
	private long createTime;
	
	/**
	 * 简介
	 */
	@Column(name = "detail",type = MySqlTypeConstant.TEXT)
	private String detail;
	
	public Equipment() {}
	
	public Equipment(String id, String name, String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
