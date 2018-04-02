package com.telecom.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

@Table(name="model")
public class GetModel extends BaseModel{
	
	@Column(name = "ModelId",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String id;
	
	@Column(name = "EquipmentId",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String equipid;
	
	@Column(name = "ModelType",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String modeltype;
	
	@Column(name = "Modelparam",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String param;
	
	public GetModel(){};
	public GetModel(String id,String equipid,String modeltype,String param)
	{
		this.id = id;
		this.equipid=equipid;
		this.modeltype=modeltype;
		this.param=param;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEquipid() {
		return equipid;
	}
	public void setEquipid(String equipid) {
		this.equipid = equipid;
	}
	public String getModeltype() {
		return modeltype;
	}
	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
