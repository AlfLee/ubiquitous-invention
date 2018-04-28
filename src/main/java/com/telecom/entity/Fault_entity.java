package com.telecom.entity;


import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

@Table(name="fault")
public class Fault_entity extends BaseModel{

	@Column(name = "FaultId",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String id;
	@Column(name = "IsFault",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String faultresult;
	@Column(name = "EquipName",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String equipname;
	@Column(name = "AlgType",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String algtype;

	@Column(name = "Time",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String time;
	@Column(name = "ModelParam",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String algparam;
	@Column(name = "EquipId",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String equipid;
	@Column(name = "FaultData",type = MySqlTypeConstant.VARCHAR)
	private String faultdata;
	public String getFaultdata() {
		return faultdata;
	}
	public void setFaultdata(String faultdata) {
		this.faultdata = faultdata;
	}
	public String getId() {
		return id;
	}
	public String getEquipid() {
		return equipid;
	}
	public void setEquipid(String equipid) {
		this.equipid = equipid;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFaultresult() {
		return faultresult;
	}
	public void setFaultresult(String faultresult) {
		this.faultresult = faultresult;
	}
	public String getEquipname() {
		return equipname;
	}
	public void setEquipname(String equipname) {
		this.equipname = equipname;
	}
	public String getAlgtype() {
		return algtype;
	}
	public void setAlgtype(String algtype) {
		this.algtype = algtype;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAlgparam() {
		return algparam;
	}
	public void setAlgparam(String algparam) {
		this.algparam = algparam;
	}
	
}
