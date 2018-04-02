package com.telecom.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 类hibernate 自动更新表结构或者创建表
 * 数据库基本信息临时表
 * @author Administrator
 *
 */
@Table(name="database_info")
public class DataBaseInfo extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据库id
	 */
	@Column(name = "id",type = MySqlTypeConstant.VARCHAR,length = 111,isKey=true)
	private String id;
	
	/**
	 * ip
	 */
	@Column(name = "ip",type = MySqlTypeConstant.VARCHAR, length=111)
	private String ip;
	
	/**
	 * 端口
	 */
	@Column(name = "port",type = MySqlTypeConstant.VARCHAR, length=111)
	private String port;

	/**
	 * 用户名
	 */
	@Column(name = "user_name",type = MySqlTypeConstant.VARCHAR, length=111)
	private String userName;
	
	/**
	 * 密码
	 */
	@Column(name = "password",type = MySqlTypeConstant.VARCHAR, length=111)
	private String password;
	
	/**
	 * 数据库类型
	 */
	@Column(name = "database_type",type = MySqlTypeConstant.VARCHAR, length=111)
	private String databaseType;
	
	/**
	 * 服务或SID
	 */
	@Column(name = "name_or_SID",type = MySqlTypeConstant.VARCHAR, length=111)
	private String nameOrSID;

	/**
	 * 服务名
	 */
	@Column(name = "server_name",type = MySqlTypeConstant.VARCHAR, length=111)
	private String serverName;
	
	public DataBaseInfo() {}

	
	public DataBaseInfo(String ip, String port, String userName, String password,
			String databaseType, String nameOrSID, String serverName) {
		super();
		this.ip = ip;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.databaseType = databaseType;
		this.nameOrSID = nameOrSID;
		this.serverName = serverName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getNameOrSID() {
		return nameOrSID;
	}

	public void setNameOrSID(String nameOrSID) {
		this.nameOrSID = nameOrSID;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
}
