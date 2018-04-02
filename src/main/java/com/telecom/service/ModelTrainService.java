package com.telecom.service;

import java.util.List;

import com.telecom.entity.DataInfo;
import com.telecom.entity.GetModel;
import com.telecom.entity.SvmModel_entity;

public interface ModelTrainService {
	public void insert(String equipid, String modeltype,String svmmodelparam);
	public List<GetModel> Showsvmmodel(String equipId,int current,int limit);
	public int svmmodelcount(String equipId);
	public String GetEquipName(String id);
	public List<DataInfo> getdataselect(String equipId);
	public void deletemodel(String id);
}
