package com.telecom.service;

import java.util.List;

import com.telecom.entity.Fault_entity;

public interface FaultService {
	public String GetModelid(String equipId,String algType);
	public String getequipname(String equipId);
	public List<Fault_entity> GetModel(String equipId,int current,int pageSize);
	public int getfaultcount(String equipId);
	public List<String> getsqldata(String dataid);
	public String GetModel(String modelid);
	public String GetModelParam(String modelid);
	public void insertresult(String faultresult,String EquipName,String faultparam,String alg_type,String equipid,String time,String faultdata);
	public void diagnose(String dataSelect,String alg_type,String equipid);
	public String getsimpledata(String FaultDataId);
}
